package com.bettercloud.challenges._2017._01Jan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by davidesposito on 1/20/17.
 */
public class _012017_ReverseratorTests {

    private _012017_Reverserator.Reverserator reverserator;

    @Before
    public void setup() {
        this.reverserator = new _012017_Reverserator.MyReverserator();
    }

    @Test
    public void testTrivial() {
        Assert.assertEquals(0, reverserator.process(0));
    }

    @Test
    public void testTrivial01() {
        Assert.assertEquals(1, reverserator.process(1));
    }

    @Test
    public void testSmall() {
        Assert.assertEquals(123, reverserator.process(321));
    }

    @Test
    public void testSmall01() {
        Assert.assertEquals(368, reverserator.process(863));
    }

    @Test
    public void testLarge() {
        Assert.assertEquals(123456789, reverserator.process(987654321));
    }

    @Test
    public void testLarge01() {
        Assert.assertEquals(737892184, reverserator.process(481298737));
    }

    @Test
    public void testEdgeCase() {
        Assert.assertEquals(987654321, reverserator.process(1234567890));
    }

    @Test
    public void testEdgeCase01() {
        Assert.assertEquals(1, reverserator.process(1000000));
    }
}
