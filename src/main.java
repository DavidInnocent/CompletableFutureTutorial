
import java.util.concurrent.CompletableFuture;

public class main {
    public static void main(String[] args) {
        /**
        *
        * THENAPPLY is used to pass data to the next completable NB you can chain then apply
         * It takes in a (function) that is ....takes in one value then produces or returns one result
         *
        * */


        CompletableFuture.supplyAsync(()-> longNetworkProcess(5))
                .thenApply(integer -> integer*20)//
                .thenApply(integer -> integer.toString()+" is the result")
                .thenAcceptAsync(System.out::println)
                .thenRun(()-> System.out.println("Operations done."));

        CompletableFuture<Integer> completableFuture=new CompletableFuture<>();
        int value=90;
        getReady(completableFuture);
        completableFuture.complete(value);
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void getReady(CompletableFuture<Integer> completableFuture) {
        completableFuture.thenApply(integer -> integer*3)
                .thenApply(integer -> "Hello "+integer)
                .thenAccept(System.out::println);
    }

    private static int longNetworkProcess(int i) {
        try {
            Thread.sleep(1000);
            return i*i;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return i*i-1;
        }

    }
}
