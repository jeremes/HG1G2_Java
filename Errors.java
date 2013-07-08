package AsteroidPhaseCurveAnalyzer;

import Jama.Matrix;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

public class Errors {

    public static double[] HG1G2Errors(Matrix aMatrix, double[] means) {
        // Creating a transpose of the A-matrix, multiplying them and taking the inverse
        Matrix transpose = aMatrix.transpose();
        Matrix invCovariance = transpose.times(aMatrix);
        Matrix covariance = invCovariance.inverse();
        double[][] covarianceArray = covariance.getArray();

        // Creating a multinormal distribution based on the mean values and a covariance matrix
        MultivariateNormalDistribution MVND = new MultivariateNormalDistribution(means, covarianceArray);

        // To change the sample size, change this (see also line 108)
        int sampleSize = 10000;

        // Creating random samples based on the distribution
        // and the rest is just calculating means and deviations 
        // for H, G1 and G2
        double[][] samples = MVND.sample(sampleSize);
        double[][] HG1G2Samples = new double[sampleSize][3];

        for (int i = 0; i < sampleSize; i++) {
            HG1G2Samples[i][0] = -2.5 * Math.log10(samples[i][0] + samples[i][1] + samples[i][2]);
            HG1G2Samples[i][1] = samples[i][0] / (samples[i][0] + samples[i][1] + samples[i][2]);
            HG1G2Samples[i][2] = samples[i][1] / (samples[i][0] + samples[i][1] + samples[i][2]);
        }

        double[] sum = new double[3];
        for (int i = 0; i < sampleSize; i++) {
            for (int j = 0; j < 3; j++) {
                sum[j] += HG1G2Samples[i][j];
            }
        }

        double[] mean = new double[sum.length];
        for (int i = 0; i < sum.length; i++) {
            mean[i] = sum[i] / sampleSize;
        }

        double[] upperVariance = new double[sum.length];
        double[] lowerVariance = new double[sum.length];
        int upperHVariance = 0;
        int lowerHVariance = 0;
        int upperG1Variance = 0;
        int lowerG1Variance = 0;
        int upperG2Variance = 0;
        int lowerG2Variance = 0;

        for (int i = 0; i < sampleSize; i++) {
            if (HG1G2Samples[i][0] >= mean[0]) {
                upperHVariance++;
                upperVariance[0] += Math.pow(HG1G2Samples[i][0] - mean[0], 2);
            } else if (HG1G2Samples[i][0] < mean[0]) {
                lowerHVariance++;
                lowerVariance[0] += Math.pow(HG1G2Samples[i][0] - mean[0], 2);
            }
        }

        for (int i = 0; i < sampleSize; i++) {
            if (HG1G2Samples[i][1] > mean[1]) {
                upperG1Variance++;
                upperVariance[1] += Math.pow(HG1G2Samples[i][1] - mean[1], 2);
            } else if (HG1G2Samples[i][1] < mean[1]) {
                lowerG1Variance++;
                lowerVariance[1] += Math.pow(mean[1] - HG1G2Samples[i][1], 2);
            }
        }


        for (int i = 0; i < sampleSize; i++) {
            if (HG1G2Samples[i][2] > mean[2]) {
                upperG2Variance++;
                upperVariance[2] += Math.pow(HG1G2Samples[i][2] - mean[2], 2);
            } else if (HG1G2Samples[i][2] < mean[2]) {
                lowerG2Variance++;
                lowerVariance[2] += Math.pow(mean[2] - HG1G2Samples[i][2], 2);
            }
        }

        double[] upperStdDeviation = new double[sum.length];
        double[] lowerStdDeviation = new double[sum.length];

        upperStdDeviation[0] = Math.sqrt(upperVariance[0] / (upperHVariance - 3));
        lowerStdDeviation[0] = Math.sqrt(lowerVariance[0] / (lowerHVariance - 3));
        upperStdDeviation[1] = Math.sqrt(upperVariance[1] / (upperG1Variance - 3));
        lowerStdDeviation[1] = Math.sqrt(lowerVariance[1] / (lowerG1Variance - 3));
        upperStdDeviation[2] = Math.sqrt(upperVariance[2] / (upperG2Variance - 3));
        lowerStdDeviation[2] = Math.sqrt(lowerVariance[2] / (lowerG2Variance - 3));

        double[] errors = new double[3 * sum.length];
        for (int i = 0; i < sum.length; i++) {
            errors[i] = mean[i];
            errors[sum.length + i] = upperStdDeviation[i];
            errors[2 * sum.length + i] = lowerStdDeviation[i];
        }

        return errors;

    }

    public static double[] HG12Errors(Matrix aMatrix, double[] means) {
        // Same as the above
        Matrix transpose = aMatrix.transpose();
        Matrix invCovariance = transpose.times(aMatrix);
        Matrix covariance = invCovariance.inverse();
        double[][] covarianceArray = covariance.getArray();

        MultivariateNormalDistribution MVND = new MultivariateNormalDistribution(means, covarianceArray);

        // To change sample size, change this
        int sampleSize = 100000;
        double[][] samples = MVND.sample(sampleSize);

        double[] sum = new double[3];
        double[][] HG1G2Samples = new double[sampleSize][3];
        for (int i = 0; i < sampleSize; i++) {
            //conversion from HG12 to HG1G2
            HG1G2Samples[i] = c1c2ToHG1G2(samples[i]);
            for (int j = 0; j < 3; j++) {
                sum[j] += HG1G2Samples[i][j];
            }
        }

        double[] mean = new double[sum.length];
        for (int i = 0; i < sum.length; i++) {
            mean[i] = sum[i] / sampleSize;
        }

        double[] upperVariance = new double[sum.length];
        double[] lowerVariance = new double[sum.length];
        int upperHVariance = 0;
        int lowerHVariance = 0;
        int upperG1Variance = 0;
        int lowerG1Variance = 0;
        int upperG2Variance = 0;
        int lowerG2Variance = 0;

        for (int i = 0; i < sampleSize; i++) {
            if (HG1G2Samples[i][0] >= mean[0]) {
                upperHVariance++;
                upperVariance[0] += Math.pow(HG1G2Samples[i][0] - mean[0], 2);
            } else if (HG1G2Samples[i][0] < mean[0]) {
                lowerHVariance++;
                lowerVariance[0] += Math.pow(HG1G2Samples[i][0] - mean[0], 2);
            }
        }

        for (int i = 0; i < sampleSize; i++) {
            if (HG1G2Samples[i][1] > mean[1]) {
                upperG1Variance++;
                upperVariance[1] += Math.pow(HG1G2Samples[i][1] - mean[1], 2);
            } else if (HG1G2Samples[i][1] < mean[1]) {
                lowerG1Variance++;
                lowerVariance[1] += Math.pow(mean[1] - HG1G2Samples[i][1], 2);
            }
        }

        for (int i = 0; i < sampleSize; i++) {
            if (HG1G2Samples[i][2] > mean[2]) {
                upperG2Variance++;
                upperVariance[2] += Math.pow(HG1G2Samples[i][2] - mean[2], 2);
            } else if (HG1G2Samples[i][2] < mean[2]) {
                lowerG2Variance++;
                lowerVariance[2] += Math.pow(mean[2] - HG1G2Samples[i][2], 2);
            }
        }

        double[] upperStdDeviation = new double[sum.length];
        double[] lowerStdDeviation = new double[sum.length];

        upperStdDeviation[0] = Math.sqrt(upperVariance[0] / (upperHVariance - 3));
        lowerStdDeviation[0] = Math.sqrt(lowerVariance[0] / (lowerHVariance - 3));
        upperStdDeviation[1] = Math.sqrt(upperVariance[1] / (upperG1Variance - 3));
        lowerStdDeviation[1] = Math.sqrt(lowerVariance[1] / (lowerG1Variance - 3));
        upperStdDeviation[2] = Math.sqrt(upperVariance[2] / (upperG2Variance - 3));
        lowerStdDeviation[2] = Math.sqrt(lowerVariance[2] / (lowerG2Variance - 3));

        double[] errors = new double[3 * sum.length];
        for (int i = 0; i < sum.length; i++) {
            errors[i] = mean[i];
            errors[sum.length + i] = upperStdDeviation[i];
            errors[2 * sum.length + i] = lowerStdDeviation[i];
        }

        return errors;

    }

    // conversion from c1,c2 to H,G1,G2
    public static double[] c1c2ToHG1G2(double[] coeffs) {
        double[] HG1G2 = new double[3];
        double b10 = 0.06164;
        double b11 = 0.7527;
        double g10 = 0.6270;
        double g11 = -0.9612;

        double b20 = 0.02162;
        double b21 = 0.9529;
        double g20 = 0.5572;
        double g21 = -0.6125;

        HG1G2[0] = -2.5 * Math.log10(coeffs[0]);

        if (coeffs[1] < 0.2) {
            HG1G2[1] = b11 * coeffs[1] + b10;
            HG1G2[2] = g11 * coeffs[1] + g10;
        } else {
            HG1G2[1] = b21 * coeffs[1] + b20;
            HG1G2[2] = g21 * coeffs[1] + g20;
        }

        return HG1G2;
    }
}