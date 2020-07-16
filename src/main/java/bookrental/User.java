package bookrental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="User_table")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String userName;
    private Date regTime;
    private Long rentalId;
    private Integer point;

    public User(){
        Date date = new Date();
        this.regTime = new Timestamp(date.getTime());
        System.out.println("regTime!! : " + this.regTime);
    }
    @PostPersist
    public void onPostPersist(){

        System.out.println("##### onPostPersist!! Id: " + this.getId());

        bookrental.UserRegistered userRegistered = new UserRegistered();
        BeanUtils.copyProperties(this, userRegistered);
        userRegistered.publishAfterCommit();

       bookrental.external.Point point = new bookrental.external.Point();
        point.setUserId(this.getId());
        point.setPoint(10);
        point.setStatus("saved");
        point.setUserTotalPoint(10);
        point.setChgDate(this.getRegTime());
        UserApplication.applicationContext.getBean(bookrental.external.PointService.class)
                .saved(point);



    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getRegTime() {
        return regTime;
    }
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Long getRentalId() {
        return rentalId;
    }
    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }


    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }



}
