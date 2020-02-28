package text;

import com.itheima.health.controller.CheckItemController;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;

/**
 * @program: health_parent
 * @ClassName text
 * @description:
 * @author: dyl
 * @create: 2020-02-16 20:46
 **/

public class text{
    @Test
    public static void f() {
        CheckItemController checkItemController = new CheckItemController();
        checkItemController.findAll();
    }



}