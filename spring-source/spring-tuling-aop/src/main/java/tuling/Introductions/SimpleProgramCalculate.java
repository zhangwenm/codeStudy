package tuling.Introductions;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class SimpleProgramCalculate implements ProgramCalculate{
    @Override
    public String toBinary(Integer value) {
        return Integer.toBinaryString(value);
    }
}
