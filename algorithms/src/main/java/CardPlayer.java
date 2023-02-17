import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zwm
 * @dec
 * @date 2023年02月16日 16:04
 */
public class CardPlayer implements Runnable{
    private ReentrantLock lock;
    private Condition takeCard;

    private List<Integer> holdCard;
    private List<Integer> cardStack;
    private int order;
    private String name;
    public CardPlayer(ReentrantLock lock, Condition takeCard, List<Integer> cardStack, int order, String name) {
        this.lock = lock;
        this.takeCard = takeCard;
        this.cardStack = cardStack;
        this.order = order;
        this.name = name;

        this.holdCard = new ArrayList<>(17);
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (cardStack.size() > 0) {
                while (( 54-cardStack.size()) % 3 != order) {
                    takeCard.await();
                }
                if(cardStack.size() == 0){
                    takeCard.signalAll();
                    return;
                }
                Integer card = cardStack.remove(0);
                holdCard.add(card);
                System.out.println(String.format("%-4s 拿到牌 %02d", name, card));
                takeCard.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Integer> cardStack = IntStream.rangeClosed(1, 54).boxed().collect(Collectors.toList());
//        Collections.shuffle(list);

        ReentrantLock lock = new ReentrantLock();
        Condition takeCard = lock.newCondition();
        CardPlayer p1 = new CardPlayer(lock, takeCard, cardStack, 0, "地主");
        CardPlayer p2 = new CardPlayer(lock, takeCard, cardStack, 1, "农民1");
        CardPlayer p3 = new CardPlayer(lock, takeCard, cardStack, 2, "农民2");

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(p1);
        executorService.execute(p2);
        executorService.execute(p3);

        executorService.shutdown();
    }
}
