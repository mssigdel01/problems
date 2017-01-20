package com.bettercloud.challenges._2017._01Jan;

import com.google.common.collect.Lists;

import java.util.Arrays;

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

    public static class DavidReverserator implements Reverserator {

        @Override
        public int process(int num) {
            return Lists.newArrayList(num).stream()
                    .map(n -> n + "")
                    .map(numString -> numString.split(""))
                    .flatMap(numStringArray -> Arrays.stream(numStringArray))
                    .reduce((a, b) -> b + a)
                    .map(numString -> Integer.parseInt(numString))
                    .orElse(0);
        }
    }
}
