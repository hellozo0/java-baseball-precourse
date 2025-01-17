package baseball;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    @Test
    void 게임종료_후_재시작() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("246", "135", "1", "597", "589", "2");
                    assertThat(output()).contains("낫싱", "3스트라이크", "1볼 1스트라이크", "3스트라이크", "게임 종료");
                },
                1, 3, 5, 5, 8, 9
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1234"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("유저가 숫자가 아닌 값을 입력했을 경우")
    void 유저_숫자이외의입력_예외_테스트() throws NoSuchMethodException{

        //given
        Game game = new Game();
        Method method = game.getClass().getDeclaredMethod("isRange", String.class);
        method.setAccessible(true);

        String test = "a23";

        //when

        //then
        try {
            method.invoke(game, test);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InvocationTargetException e){
            assertThat(e.getCause().getMessage()).isEqualTo("1~9 범위의 숫자가 아닙니다.");
        }
    }

    @Test
    @DisplayName("유저가 3글자가 아닌 값을 입력했을 경우")
    void 유저_숫자길이입력_예외_테스트() throws NoSuchMethodException {

        //given
        Game game = new Game();
        Method method = game.getClass().getDeclaredMethod("isLength", String.class);
        method.setAccessible(true);

        String test = "1234";

        //when

        //then
        try {
            method.invoke(game, test);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InvocationTargetException e){
            assertThat(e.getCause().getMessage()).isEqualTo("3개의 숫자가 아닙니다.");
        }
    }

    @Test
    @DisplayName("유저가 중복된 숫자를 입력했을 경우")
    void 유저_숫자중복입력_예외_테스트() throws NoSuchMethodException{

        //given
        Game game = new Game();
        Method method = game.getClass().getDeclaredMethod("isDuplicate", String.class);
        method.setAccessible(true);

        String test = "112";

        //when

        //then
        try {
            method.invoke(game, test);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            assertThat(e.getCause().getMessage()).isEqualTo("중복되는 숫자를 입력했습니다.");
        }
    }

    @Test
    @DisplayName("컴퓨터가 중복된 숫자를 생성했을 경우")
    void 컴퓨터_숫자생성_테스트(){

        //given
        List<Integer> computerNum = new Game().getComputerNum();

        //when
        int size = computerNum.size();

        //then
        assertThat(size).isEqualTo(3);
        assertThat(new HashSet<>(size)).isEqualTo(3);
    }

    @Test
    @DisplayName("게임의 볼 개수 테스트")
    void 게임_볼_검증테스트(){

        //given
        List<Integer> computerNum = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> userNum = new ArrayList<>(Arrays.asList(2, 1, 9));

        //when
        int ball = new Game().checkBall(computerNum, userNum);

        //then
        assertThat(ball).isEqualTo(2);
    }

    @Test
    @DisplayName("게임의 스트라이크 개수 테스트")
    void 게임_스트라이크_검증테스트(){

        //given
        List<Integer> computerNum = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> userNum = new ArrayList<>(Arrays.asList(1, 2, 9));

        //when
        int strike = new Game().checkStrike(computerNum, userNum);

        //then
        assertThat(strike).isEqualTo(2);
    }

    @Test
    @DisplayName("게임의 볼과 스트라이크 개수 테스트")
    void 게임_볼_스트라이크_검증테스트(){

        //given
        List<Integer> computerNum = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> userNum = new ArrayList<>(Arrays.asList(1, 3, 9));

        //when
        int strike = new Game().checkStrike(computerNum, userNum);
        int ball = new Game().checkBall(computerNum, userNum);

        //then
        assertThat(strike).isEqualTo(1);
        assertThat(ball).isEqualTo(2);
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
