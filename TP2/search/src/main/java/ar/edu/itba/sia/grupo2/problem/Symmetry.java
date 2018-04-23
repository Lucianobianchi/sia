package ar.edu.itba.sia.grupo2.problem;

public enum Symmetry {
    ROT_90{
        @Override
        public Coordinate transform(Coordinate coordinate, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(coordinate.getColumn(), maxCoord - coordinate.getRow());
        }
    },

    ROT_180{
        @Override
        public Coordinate transform(Coordinate coordinate, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(maxCoord - coordinate.getRow(), maxCoord - coordinate.getColumn());
        }
    },

    ROT_270{
        @Override
        public Coordinate transform(Coordinate coordinate, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(maxCoord - coordinate.getColumn(), coordinate.getRow());
        }
    },

    FLIP_HOR{
        @Override
        public Coordinate transform(Coordinate coordinate, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(coordinate.getRow(), maxCoord - coordinate.getColumn());
        }
    },


    FLIP_VER{
            @Override
            public Coordinate transform(Coordinate coordinate, int dimension) {
                int maxCoord = dimension - 1;
                return new Coordinate(maxCoord - coordinate.getRow(), coordinate.getColumn());
            }
    },

    DIAG_1{
        @Override
        public Coordinate transform(Coordinate coordinate, int dimension) {
            return new Coordinate(coordinate.getColumn(), coordinate.getRow());
        }
    },

    DIAG_2{
        @Override
        public Coordinate transform(Coordinate coordinate, int dimension) {
            int maxCoord = dimension - 1;
            return new Coordinate(maxCoord - coordinate.getColumn(), maxCoord - coordinate.getRow());
        }
    };

    public abstract Coordinate transform(Coordinate coordinate, int dimension);

}
