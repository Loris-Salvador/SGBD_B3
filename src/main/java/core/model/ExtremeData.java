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
        private double accX;
        private double accY;
        private double accZ;
        private double gyroX;
        private double gyroY;
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

        @Override
        public String toString() {
            return "DataMin{" +
                    "accX=" + accX +
                    ", accY=" + accY +
                    ", accZ=" + accZ +
                    ", gyroX=" + gyroX +
                    ", gyroY=" + gyroY +
                    ", gyroZ=" + gyroZ +
                    '}';
        }
    }

    public class DataMax {
        private double accX;
        private double accY;
        private double accZ;
        private double gyroX;
        private double gyroY;
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

        @Override
        public String toString() {
            return "DataMax{" +
                    "accX=" + accX +
                    ", accY=" + accY +
                    ", accZ=" + accZ +
                    ", gyroX=" + gyroX +
                    ", gyroY=" + gyroY +
                    ", gyroZ=" + gyroZ +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ExtremeData{" +
                "dataMin=" + dataMin.toString() +
                ", dataMax=" + dataMax.toString() +
                '}';
    }

    public ExtremeData getCeilValue()
    {
        ExtremeData tmp = new ExtremeData();
        tmp.getDataMin().setAccX(Math.floor(dataMin.getAccX()));
        tmp.getDataMin().setAccY(Math.floor(dataMin.getAccY()));
        tmp.getDataMin().setAccZ(Math.floor(dataMin.getAccZ()));
        tmp.getDataMin().setGyroX(Math.floor(dataMin.getGyroX()));
        tmp.getDataMin().setGyroY(Math.floor(dataMin.getGyroY()));
        tmp.getDataMin().setGyroZ(Math.floor(dataMin.getGyroZ()));
        tmp.getDataMax().setAccX(Math.ceil(dataMax.getAccX()));
        tmp.getDataMax().setAccY(Math.ceil(dataMax.getAccY()));
        tmp.getDataMax().setAccZ(Math.ceil(dataMax.getAccZ()));
        tmp.getDataMax().setGyroX(Math.ceil(dataMax.getGyroX()));
        tmp.getDataMax().setGyroY(Math.ceil(dataMax.getGyroY()));
        tmp.getDataMax().setGyroZ(Math.ceil(dataMax.getGyroZ()));
        return tmp;
    }

    public double getDataMax(int i)
    {
        return switch (i) {
            case 0 -> dataMax.accX;
            case 1 -> dataMax.accY;
            case 2 -> dataMax.accZ;
            case 3 -> dataMax.gyroX;
            case 4 -> dataMax.gyroY;
            case 5 -> dataMax.gyroZ;
            default -> dataMax.accX;
        };
    }

    public double getDataMin(int i)
    {
        return switch (i) {
            case 0 -> dataMin.accX;
            case 1 -> dataMin.accY;
            case 2 -> dataMin.accZ;
            case 3 -> dataMin.gyroX;
            case 4 -> dataMin.gyroY;
            case 5 -> dataMin.gyroZ;
            default -> dataMin.accX;
        };
    }
}
