package com.bettercloud.challenges._2017._01Jan;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.pow;

/**
 * Created by davidesposito on 1/20/17.
 */
public class _012017_Reverserator {

    public interface Reverserator {

        /**
         * Reverses the provided integer. e.g.
         *
         *      1 => 1
         *      12 => 21
         *      123 => 321
         *      45737892184 => 48129873754
         *
         * @param num A positive integer
         * @return The reverse of the positive integer
         */
        int process(int num);
    }

    public static class MyReverserator implements Reverserator {

        @Override
        public int process(int num) {
            /** Solution 1
             return Integer.valueOf(StreamSupport.stream(Spliterators.spliteratorUnknownSize(Integer.toString(num)
             .chars()
             .map(c->c-'0')
             .boxed()
             .map(Object::toString)
             .collect(Collectors.toCollection(LinkedList::new))
             .descendingIterator(), Spliterator.ORDERED), false).collect(Collectors.joining()));
             */
            /** Solution 2 */
            return Integer.valueOf(IntStream.range(0, String.valueOf(num).length()).map(i -> (int)((num % pow(10, i + 1) - num % pow(10, i)) / pow(10, i)))
                    .boxed()
                    .map((Object::toString))
                    .collect(Collectors.joining()));
        }
    }
}
