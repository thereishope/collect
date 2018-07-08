import com.data.gateway.GateWayApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title RedisTest
 * @project collect
 * @date 2018/6/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GateWayApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        ValueOperations connection = redisTemplate.opsForValue();

        System.out.print(connection.get("auth"));
    }
}
