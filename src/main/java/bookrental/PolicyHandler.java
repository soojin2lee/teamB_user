package bookrental;

import bookrental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    UserRepository userRepo;
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRented_ChangeRentalStatus(@Payload Rented rented){
        /*대여 완료 시 대여id 업데이트*/
        if(rented.isMe()){
            System.out.println("대여완료-->user Policy : " + rented.toJson());

            Optional<User> userOptional = userRepo.findById(rented.getUserId());
            if( userOptional.isPresent() ) {
                User user = userOptional.get();
                user.setRentalId(rented.getId());

                userRepo.save(user);
            }
            else {
                System.out.println("cant not find user ID!! : " + rented.getUserId());
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReturned_ChangeRentalStatus(@Payload Returned returned){
        /*반납 시 대여 id 삭제*/
        if(returned.isMe()){
            System.out.println("##### listener ChangeRentalStatus : " + returned.toJson());

            Optional<User> userOptional = userRepo.findById(returned.getUserId());
            if( userOptional.isPresent() ) {
                User user = userOptional.get();
                user.setRentalId(null);

                userRepo.save(user);
            }
            else {
                System.out.println("cant not find user ID!! : " + returned.getUserId());
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelled_ChangeRentalStatus(@Payload Cancelled cancelled){
        /*대여 취소 시 대여 id 삭제*/
        if(cancelled.isMe()){
            System.out.println("##### listener ChangeRentalStatus : " + cancelled.toJson());

            Optional<User> userOptional = userRepo.findById(cancelled.getUserId());
            if( userOptional.isPresent() ) {
                User user = userOptional.get();
                user.setRentalId(null);

                userRepo.save(user);
            }
            else {
                System.out.println("cant not find user ID!! : " + cancelled.getUserId());
            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverSaved_ChangeRentalStatus(@Payload Saved saved){

        if(saved.isMe()){
            System.out.println("##### listener ChangeRentalStatus : " + saved.toJson());
            Optional<User> userOptional = userRepo.findById(saved.getUserId());
            if( userOptional.isPresent() ) {
                User user = userOptional.get();
                user.setPoint(saved.getUserTotalPoint());
                userRepo.save(user);
            }
            else {
                System.out.println("cant not find user ID!! : " + saved.getUserId());
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverUsed_ChangeRentalStatus(@Payload Used used){

        if(used.isMe()){
            System.out.println("##### listener ChangeRentalStatus : " + used.toJson());
            Optional<User> userOptional = userRepo.findById(used.getUserId());
            if( userOptional.isPresent() ) {
                User user = userOptional.get();
                user.setPoint(used.getUserTotalPoint());

                userRepo.save(user);
            }
            else {
                System.out.println("cant not find user ID!! : " + used.getUserId());
            }
        }
    }

}
