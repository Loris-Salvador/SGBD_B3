package core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtremeData {
    private DataMin dataMin;
    private DataMax dataMax;

    public ExtremeData(){
        dataMin = new DataMin();
        dataMax = new DataMax();
    }

    public DataMin getDataMin() {
        return dataMin;
    }

    public void setDataMin(DataMin dataMin) {
        this.dataMin = dataMin;
    }

    public DataMax getDataMax() {
        return dataMax;
    }

    public void setDataMax(DataMax dataMax) {
        this.dataMax = dataMax;
    }

    public class DataMin {
        @JsonProperty("accx")
        private double accX;
        @JsonProperty("accy")
        private double accY;
        @JsonProperty("accz")
        private double accZ;
        @JsonProperty("gyrox")
        private double gyroX;
        @JsonProperty("gyroy")
        private double gyroY;
        @JsonProperty("gyroz")
        private double gyroZ;

        public double getAccX() {
            return accX;
        }

        public void setAccX(double accX) {
            this.accX = accX;
        }

        public double getAccY() {
            return accY;
        }

        public void setAccY(double accY) {
            this.accY = accY;
        }

        public double getAccZ() {
            return accZ;
        }

        public void setAccZ(double accZ) {
            this.accZ = accZ;
        }

        public double getGyroX() {
            return gyroX;
        }

        public void setGyroX(double gyroX) {
            this.gyroX = gyroX;
        }

        public double getGyroY() {
            return gyroY;
        }

        public void setGyroY(double gyroY) {
            this.gyroY = gyroY;
        }

        public double getGyroZ() {
            return gyroZ;
        }

        public void setGyroZ(double gyroZ) {
            this.gyroZ = gyroZ;
        }
    }

    public class DataMax {
        @JsonProperty("accx")
        private double accX;
        @JsonProperty("accy")
        private double accY;
        @JsonProperty("accz")
        private double accZ;
        @JsonProperty("gyrox")
        private double gyroX;
        @JsonProperty("gyroy")
        private double gyroY;
        @JsonProperty("gyroz")
        private double gyroZ;

        public double getAccX() {
            return accX;
        }

        public void setAccX(double accX) {
            this.accX = accX;
        }

        public double getAccY() {
            return accY;
        }

        public void setAccY(double accY) {
            this.accY = accY;
        }

        public double getAccZ() {
            return accZ;
        }

        public void setAccZ(double accZ) {
            this.accZ = accZ;
        }

        public double getGyroX() {
            return gyroX;
        }

        public void setGyroX(double gyroX) {
            this.gyroX = gyroX;
        }

        public double getGyroY() {
            return gyroY;
        }

        public void setGyroY(double gyroY) {
            this.gyroY = gyroY;
        }

        public double getGyroZ() {
            return gyroZ;
        }

        public void setGyroZ(double gyroZ) {
            this.gyroZ = gyroZ;
        }
    }

}
