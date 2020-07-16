
package bookrental.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name ="point", url="${api.url.point}")
public interface PointService {

    @RequestMapping(method= RequestMethod.POST, path="/points/save")
    public void saved(@RequestBody Point point);

}