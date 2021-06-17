
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
                .thenRun(()-> System.out.println("Operations done.")).join();

        CompletableFuture.supplyAsync(()-> longNetworkProcess(5))
                .completeOnTimeout(99,2000, TimeUnit.SECONDS)//if timesout after one second return 99
                .thenApply(integer -> integer.toString()+" is the result")
                .thenAcceptAsync(System.out::println)
                .thenRun(()-> System.out.println("Operations done.")).join();

        CompletableFuture<Integer> completableFuture=new CompletableFuture<>();
        int value=90;
        getReady(completableFuture);
        completableFuture.complete(value);

/**
 *
 * thenCombine will run all the completables at once then combine the results after all have finished. if one finishes before the other. It has to wait for the other
 * to finish
 *
 * */
        int amount=10;
        getValue1(amount)
                .thenCombine(getValue2(amount),((integer, integer2) -> integer.toString()+" and the next "+integer2.toString()))
                .thenCombine(getValue3(5),((s, integer) -> s+"looping "+integer))
                .thenAccept(
                        s -> {
                            System.out.println();
                            int numb=Integer.parseInt(s.substring(s.length() - 1));

//                           int numb=Integer.getInteger(String.valueOf(s.substring(s.length()-1)));

                           int lop=0;
                           while(numb>lop)
                           {
                               System.out.println(s);
                               lop++;
                           }
                        }
        ).join();

        /**
        *
         * thencompose will pipeline the result of one completable to the next one in a chain
        * */

        getValue1(amount).thenComposeAsync((integer -> getValue2(integer).thenComposeAsync(main::getValue3))).thenAccept(System.out::println);



        try {
            Thread.sleep(5100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void getReady(CompletableFuture<Integer> completableFuture) {
        completableFuture.thenApply(integer -> integer*3)
                .thenApply(integer -> "Hello "+integer)
                .thenAccept(System.out::println);
    }
    private static CompletableFuture<Integer> getValue1(int intt) {
        return CompletableFuture.supplyAsync(() -> intt*3);
    }
    private static CompletableFuture<Integer> getValue2(int intt) {
       return CompletableFuture.supplyAsync(()->intt*5);
    }
    private static CompletableFuture<Integer> getValue3(int intt) {
       return CompletableFuture.supplyAsync(()->intt*5);
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
