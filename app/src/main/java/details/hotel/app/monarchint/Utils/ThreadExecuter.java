package details.hotel.app.monarchint.Utils;

import java.util.concurrent.Executor;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class ThreadExecuter  implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}

