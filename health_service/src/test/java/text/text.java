package text;

import com.itheima.health.service.CheckItemServiceImpl;
import org.junit.Test;

/**
 * @program: health_parent
 * @ClassName text
 * @description:
 * @author: dyl
 * @create: 2020-02-16 20:52
 **/

public class text {
    @Test
    public static void f() {
        CheckItemServiceImpl checkItemService = new CheckItemServiceImpl();
        checkItemService.findAll();
    }
}