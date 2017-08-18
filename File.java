public class File extends java.io.File {
    private long length;
    private long lastModified;

    public File(String pathname) {
        super(pathname);
        this.length = super.length();
        this.lastModified = super.lastModified();
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public File[] listFiles() {
        java.io.File[] files = super.listFiles();
        if (files == null)
            return new File[0];

        File[] myFiles = new File[files.length];
        for (int i = 0; i < files.length; i++)
            myFiles[i] = new File(files[i].getAbsolutePath());
        return myFiles;
    }

    @Override
    public long lastModified() {
        return lastModified;
    }
}
