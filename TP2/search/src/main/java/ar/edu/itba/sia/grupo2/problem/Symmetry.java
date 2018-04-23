package ar.edu.itba.sia.grupo2.problem;

public enum Symmetry {
    ROT_0{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            return new Coordinate(row, col);
        }
    },

    ROT_90{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(col, maxCoord - row);
        }
    },

    ROT_180{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(maxCoord - row, maxCoord - col);
        }
    },

    ROT_270{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(maxCoord - col, row);
        }
    },

    FLIP_HOR{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(row, maxCoord - col);
        }
    },


    FLIP_VER{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(maxCoord - row, col);
        }
    },

    DIAG_1{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            return new Coordinate(col, row);
        }
    },

    DIAG_2{
        @Override
        public Coordinate transform(int row, int col, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(maxCoord - col, maxCoord - row);
        }
    };

    public Coordinate transform(Coordinate coordinate, int dimension) {
        return transform(coordinate.getRow(), coordinate.getColumn(), dimension);
    }

    public abstract Coordinate transform(int row, int col, int dimension);
}
