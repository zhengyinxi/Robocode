package wang.xin.robocode.server.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by Xin on 02/24/2017.
 */
@Service
public class SomeService {

    @Async
    public Future<Integer> doWork() throws InterruptedException {
        System.out.println("current thread is " + Thread.currentThread().getName());
        Thread.sleep(1000L);
        return new AsyncResult<>(1);
    }
}
