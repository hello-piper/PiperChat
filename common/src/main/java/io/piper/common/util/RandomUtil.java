/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomUtil
 *
 * @author piper
 */
public class RandomUtil {
    public static final String BASE_NUMBER = "0123456789";
    public static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyz".toUpperCase() + "abcdefghijklmnopqrstuvwxyz0123456789";

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static SecureRandom createSecureRandom(byte[] seed) {
        return null == seed ? new SecureRandom() : new SecureRandom(seed);
    }

    public static SecureRandom getSecureRandom() {
        return getSecureRandom((byte[]) null);
    }

    public static SecureRandom getSecureRandom(byte[] seed) {
        return createSecureRandom(seed);
    }

    public static Random getRandom(boolean isSecure) {
        return (Random) (isSecure ? getSecureRandom() : getRandom());
    }

    public static boolean randomBoolean() {
        return 0 == randomInt(2);
    }

    public static byte[] randomBytes(int length) {
        byte[] bytes = new byte[length];
        getRandom().nextBytes(bytes);
        return bytes;
    }

    public static int randomInt() {
        return getRandom().nextInt();
    }

    public static int randomInt(int limitExclude) {
        return getRandom().nextInt(limitExclude);
    }

    public static int randomInt(int minInclude, int maxExclude) {
        return randomInt(minInclude, maxExclude, true, false);
    }

    public static int randomInt(int min, int max, boolean includeMin, boolean includeMax) {
        if (!includeMin) {
            ++min;
        }

        if (includeMax) {
            --max;
        }
        return getRandom().nextInt(min, max);
    }

    public static int[] randomInts(int length) {
        int[] range = new int[length];

        for (int i = 0; i < length; ++i) {
            int random = randomInt(i, length);
            range[i] = random;
        }

        return range;
    }

    public static long randomLong() {
        return getRandom().nextLong();
    }

    public static long randomLong(long limitExclude) {
        return getRandom().nextLong(limitExclude);
    }

    public static long randomLong(long minInclude, long maxExclude) {
        return randomLong(minInclude, maxExclude, true, false);
    }

    public static long randomLong(long min, long max, boolean includeMin, boolean includeMax) {
        if (!includeMin) {
            ++min;
        }

        if (includeMax) {
            --max;
        }

        return getRandom().nextLong(min, max);
    }

    public static float randomFloat() {
        return getRandom().nextFloat();
    }

    public static float randomFloat(float limitExclude) {
        return randomFloat(0.0F, limitExclude);
    }

    public static float randomFloat(float minInclude, float maxExclude) {
        return minInclude == maxExclude ? minInclude : minInclude + (maxExclude - minInclude) * getRandom().nextFloat();
    }

    public static double randomDouble(double minInclude, double maxExclude) {
        return getRandom().nextDouble(minInclude, maxExclude);
    }

    public static double randomDouble() {
        return getRandom().nextDouble();
    }

    public static double randomDouble(double limit) {
        return getRandom().nextDouble(limit);
    }

    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    public static String randomStringUpper(int length) {
        return randomString(BASE_CHAR_NUMBER, length).toUpperCase();
    }

    public static String randomNumbers(int length) {
        return randomString("0123456789", length);
    }

    public static String randomString(String baseString, int length) {
        if (StringUtil.isEmpty(baseString)) {
            return "";
        } else {
            if (length < 1) {
                length = 1;
            }

            StringBuilder sb = new StringBuilder(length);
            int baseLength = baseString.length();

            for (int i = 0; i < length; ++i) {
                int number = randomInt(baseLength);
                sb.append(baseString.charAt(number));
            }

            return sb.toString();
        }
    }

    public static char randomChinese() {
        return (char) randomInt(19968, 40959);
    }

    public static char randomNumber() {
        return randomChar(BASE_NUMBER);
    }

    public static char randomChar() {
        return randomChar(BASE_CHAR_NUMBER);
    }

    public static char randomChar(String baseString) {
        return baseString.charAt(randomInt(baseString.length()));
    }

}
