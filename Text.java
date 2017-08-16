import java.util.Locale;

public class Text {
    private String specialSequence = "\0";
    private String language;
    private String optionsMsg;
    private String selectOptionMsg;
    private String notFileErrorMsg;
    private String invalidOptionMsg;
    private String notDirErrorMsg;
    private String fileReadErrorMsg;
    private String dir1Msg;
    private String dir2Msg;
    private String notFoundOnDir1Msg;
    private String notFoundOnDir2Msg;
    private String notEqualContentMsg;
    private String file1Msg;
    private String file2Msg;
    private String equalFilesMsg;
    private String notEqualFilesMsg;
    private String filesNotInDir2ButWithCopiesThereMsg;
    private String deletionErrorMsg;
    private String confirmationMsg;
    private String confirmInput;
    private String denyInput;
    private String deletingFilesMsg;
    private String dirMsg;
    private String duplicateFilesMsg;
    private String loggerStartMsg;
    private String generatedLoggerMsg;
    private String foundMsg;
    private String remainingTimeMsg;
    private String calculatingMsg;
    private String invalidArgMsg;

    public Text() {
        language = Locale.getDefault().getLanguage();

        if (language.equalsIgnoreCase("pt")) {
            optionsMsg = "\nOpções:\n" +
                    "1-Comparar o conteúdo de todos os ficheiros correspondentes em dois diretórios com a mesma estrutura de ficheiros.\n" +
                    "2-Comparar o conteúdo de dois ficheiros.\n" +
                    "3-Comparar todos os ficheiros correspondentes em dois diretórios com a mesma estrutura de ficheiros por tamanho e data de modificação.\n" +
                    "4-Comparar dois ficheiros por tamanho e data de modificação.\n" +
                    "5-Listar ficheiros que estão no diretório 1, mas não estão no diretório 2 e têm cópias noutra localização no diretório 2. *\n" +
                    "6-Remover ficheiros que estão no diretório 1, mas não estão no diretório 2 e têm cópias noutra localização no diretório 2. *\n" +
                    "7-Listar todos os duplicados num dado diretório. *\n" +
                    "0-Sair.\n" +
                    "* Esta operação pode ser demorada, pois usa comparação por conteúdo.\n\n";
            selectOptionMsg = "Seleciona uma opção (exemplo: " + specialSequence + "): ";
            notFileErrorMsg = "'" + specialSequence + "' não é um ficheiro. Extensão do ficheiro em falta?";
            invalidOptionMsg = "Opção inválida.\n";
            notDirErrorMsg = "'" + specialSequence + "' não é um diretório.";
            fileReadErrorMsg = "Ocorreu um erro ao ler os dados dos ficheiros.";
            dir1Msg = "Diretório 1: ";
            dir2Msg = "Diretório 2: ";
            notFoundOnDir1Msg = "Não encontrados no diretório 1: " + specialSequence + "\n";
            notFoundOnDir2Msg = "Não encontrados no diretório 2: " + specialSequence + "\n";
            notEqualContentMsg = "Conteúdo não igual: " + specialSequence + "\n";
            file1Msg = "Ficheiro 1: ";
            file2Msg = "Ficheiro 2: ";
            equalFilesMsg = "Os ficheiros são iguais.\n";
            notEqualFilesMsg = "Os ficheiros não são iguais.\n";
            filesNotInDir2ButWithCopiesThereMsg = "Ficheiros que não estão no diretório 2, mas têm cópias lá:\n";
            deletionErrorMsg = "Não foi possível eliminar '" + specialSequence + "'.\n";
            confirmationMsg = "Tem a certeza que deseja continuar? (S/N): ";
            confirmInput = "S";
            denyInput = "N";
            deletingFilesMsg = "Eliminando os ficheiros...\n";
            dirMsg = "Diretório: ";
            duplicateFilesMsg = "Ficheiros duplicados: " + specialSequence + " = " + specialSequence + " bytes\n";
            loggerStartMsg = "Dados gerados pelo ExactCopyAnalyser serão guardados abaixo.\n\n";
            generatedLoggerMsg = "Dados adicionais serão guardados em '" + specialSequence + "'.\n";
            foundMsg = "Encontrado(s): ";
            remainingTimeMsg = "Tempo restante: ";
            calculatingMsg = "calculando...";
            invalidArgMsg = "Argumento inválido.";
        } else {
            optionsMsg = "Options:\n" +
                    "1-Compare the content of all corresponding files in two directories with the same structure of files.\n" +
                    "2-Compare the content of two files.\n" +
                    "3-Compare all corresponding files in two directories with the same structure of files by size and modification date.\n" +
                    "4-Compare two files by size and modification date.\n" +
                    "5-List files that are in directory 1, but are not in directory 2 and have copies on other location in directory 2. *\n" +
                    "6-Remove files that are in directory 1, but are not in directory 2 and have copies on other location in directory 2. *\n" +
                    "7-List all duplicates in a given directory. *\n" +
                    "0-Exit.\n" +
                    "* This operation can be time-consuming because it uses comparison by content.\n\n";
            selectOptionMsg = "Select an option (example: " + specialSequence + "): ";
            notFileErrorMsg = "'" + specialSequence + "' is not a file. File extension missing?";
            invalidOptionMsg = "Invalid option.\n";
            notDirErrorMsg = "'" + specialSequence + "' is not a directory.";
            fileReadErrorMsg = "An error occurred reading the data from the files.";
            dir1Msg = "Directory 1: ";
            dir2Msg = "Directory 2: ";
            notFoundOnDir1Msg = "Not found on directory 1: " + specialSequence + "\n";
            notFoundOnDir2Msg = "Not found on directory 2: " + specialSequence + "\n";
            notEqualContentMsg = "Not equal content: " + specialSequence;
            file1Msg = "File 1: ";
            file2Msg = "File 2: ";
            equalFilesMsg = "The files are equal.\n";
            notEqualFilesMsg = "The files are not equal.\n";
            filesNotInDir2ButWithCopiesThereMsg = "Files that are not in directory 2 but have copies there:\n";
            deletionErrorMsg = "It was not possible to delete '" + specialSequence + "'.\n";
            confirmationMsg = "Are you sure that you want to proceed? (Y/N): ";
            confirmInput = "Y";
            denyInput = "N";
            deletingFilesMsg = "Deleting files...\n";
            dirMsg = "Directory: ";
            duplicateFilesMsg = "Duplicate files: " + specialSequence + " = " + specialSequence + " bytes\n";
            loggerStartMsg = "Data generated by ExactCopyAnalyser will be stored below.\n\n";
            generatedLoggerMsg = "Additional data will be logged on '" + specialSequence + "'.\n";
            foundMsg = "Found: ";
            remainingTimeMsg = "Remaining time: ";
            calculatingMsg = "calculating...";
            invalidArgMsg = "Invalid argument.";
        }
    }

    public String getOptionsMsg() {
        return optionsMsg;
    }

    public String getSelectOptionMsg(int example) {
        return selectOptionMsg.replaceFirst(specialSequence, Integer.toString(example));
    }

    public String getNotFileErrorMsg(String filename) {
        return notFileErrorMsg.replace(specialSequence, filename);
    }

    public String getInvalidOptionMsg() {
        return invalidOptionMsg;
    }

    public String getNotDirErrorMsg(String directory) {
        return notDirErrorMsg.replaceFirst(specialSequence, directory);
    }

    public String getFileReadErrorMsg() {
        return fileReadErrorMsg;
    }

    public String getDir1Msg() {
        return dir1Msg;
    }

    public String getDir2Msg() {
        return dir2Msg;
    }

    public String getNotFoundOnDir1Msg(int number) {
        return notFoundOnDir1Msg.replaceFirst(specialSequence, Integer.toString(number));
    }

    public String getNotFoundOnDir2Msg(int number) {
        return notFoundOnDir2Msg.replaceFirst(specialSequence, Integer.toString(number));
    }

    public String getNotEqualContentMsg(int number) {
        return notEqualContentMsg.replaceFirst(specialSequence, Integer.toString(number));
    }

    public String getFile1Msg() {
        return file1Msg;
    }

    public String getFile2Msg() {
        return file2Msg;
    }

    public String getEqualFilesMsg() {
        return equalFilesMsg;
    }

    public String getNotEqualFilesMsg() {
        return notEqualFilesMsg;
    }

    public String getFilesNotInDir2ButWithCopiesThereMsg() {
        return filesNotInDir2ButWithCopiesThereMsg;
    }

    public String getDeletionErrorMsg(String name) {
        return deletionErrorMsg.replaceFirst(specialSequence, name);
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public String getConfirmInput() {
        return confirmInput;
    }

    public String getDenyInput() {
        return denyInput;
    }

    public String getDeletingFilesMsg() {
        return deletingFilesMsg;
    }

    public String getDirMsg() {
        return dirMsg;
    }

    public String getDuplicateFilesMsg(int number, long size) {
        return duplicateFilesMsg.replaceFirst(specialSequence, Integer.toString(number)).replaceFirst(specialSequence, Long.toString(size));
    }

    public String getLoggerStartMsg() {
        return loggerStartMsg;
    }

    public String getGeneratedLoggerMsg(String loggerPath) {
        return generatedLoggerMsg.replaceFirst(specialSequence, loggerPath);
    }

    public String getFoundMsg() {
        return foundMsg;
    }

    public String getRemainingTimeMsg() {
        return remainingTimeMsg;
    }

    public String getCalculatingMsg() {
        return calculatingMsg;
    }

    public String getInvalidArgMsg() {
        return invalidArgMsg;
    }
}
