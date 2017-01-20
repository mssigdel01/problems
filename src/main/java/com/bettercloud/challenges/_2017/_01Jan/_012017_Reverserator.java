package com.bettercloud.challenges._2017._01Jan;

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
            return 0;
        }
    }
}
