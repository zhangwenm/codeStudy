import org.junit.Test;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class TestAOP {

    @Test
    public void test01(){

        try {
            try {
                try {
                    System.out.println("前置");
                    System.out.println("自置");
                    System.out.println(1 / 0);
                    System.out.println("返回");
                } finally {
                    System.out.println("后置-内");
                }
            } catch (Exception ex) {
                System.out.println("异常");
            }
        }
        catch (Exception ex){

        }
    }

}
