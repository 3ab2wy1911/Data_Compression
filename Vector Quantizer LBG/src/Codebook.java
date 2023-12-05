class Codebook {
    public int height;
    public int width;
    public double[][] data;
    public String label;


    public Codebook(){
        this.height = 0;
        this.width = 0;
        data = new double[height][width];
        label = "";

    }

    public Codebook(int height,int width,double [][] data,String label) {
        this.height = height;
        this.width = width;
        this.label = label;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

}