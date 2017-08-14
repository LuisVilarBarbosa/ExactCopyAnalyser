import java.util.Locale;

public class Text {
    private String specialSequence = "\0";
    private String language;
    private String optionsMsg;
    private String selectOptionMsg;
    private String notFileErrorMsg;
    private String invalidOptionMsg;
    private String dir1Msg;
    private String dir2Msg;
    private String notFoundOnDir1Msg;
    private String notFoundOnDir2Msg;
    private String file1Msg;
    private String file2Msg;
    private String deletionErrorMsg;
    private String confirmationMsg;
    private String confirmInput;
    private String denyInput;
    private String dirMsg;
    private String generatedLoggerMsg;
    private String foundMsg;

    public Text() {
        language = Locale.getDefault().getLanguage();

        if (language.equalsIgnoreCase("pt")) {
            optionsMsg = "\nOpções:\n" +
                    "1. Comparar o conteúdo de todos os ficheiros correspondentes em dois diretórios com a mesma estrutura.\n" +
                    "2. Comparar o conteúdo de dois ficheiros.\n" +
                    "3. Comparar todos os ficheiros correspondentes em dois diretórios com a mesma estrutura por tamanho e data de modificação.\n" +
                    "4. Comparar dois ficheiros por tamanho e data de modificação.\n" +
                    "5. Listar ficheiros que estão no diretório origem, mas não estão no diretório destino e têm ficheiros iguais noutra localização no destino. (Operação possivelmente demorada)\n" +
                    "6. Remover ficheiros que estão no diretório origem, mas não estão no diretório destino e têm ficheiros iguais noutra localização no destino. (Operação possivelmente demorada)\n" +
                    "7. Listar todos os duplicados num dado diretório. (Operação possivelmente demorada)\n" +
                    "0. Sair.\n";
            selectOptionMsg = "Seleciona uma opção (exemplo: " + specialSequence + "): ";
            notFileErrorMsg = "'" + specialSequence + "' não é um ficheiro. Extensão do ficheiro em falta?";
            invalidOptionMsg = "Opção inválida.\n";
            dir1Msg = "Diretório 1: ";
            dir2Msg = "Diretório 2: ";
            notFoundOnDir1Msg = "Não encontrados no diretório 1: " + specialSequence + "\n";
            notFoundOnDir2Msg = "Não encontrados no diretório 2: " + specialSequence + "\n";
            file1Msg = "Ficheiro 1: ";
            file2Msg = "Ficheiro 2: ";
            deletionErrorMsg = "Não foi possível remover '" + specialSequence + "'.\n";
            confirmationMsg = "Tem a certeza que deseja continuar? (S/N): ";
            confirmInput = "S";
            denyInput = "N";
            dirMsg = "Diretório: ";
            generatedLoggerMsg = "Dados adicionais serão guardados em '" + specialSequence + "'.\n";
            foundMsg = "Encontrado(s)";
        } else {
            optionsMsg = "\nOptions:\n" +
                    "1. Compare the content of all corresponding files in two directories with the same structure.\n" +
                    "2. Compare the content of two files.\n" +
                    "3. Compare all corresponding files in two directories with the same structure by size and modification date.\n" +
                    "4. Compare two files by size and modification date.\n" +
                    "5. List files that are in the source directory, but are not in the destination directory and have equal files placed on other location in the destination. (Possibly time consuming operation)\n" +
                    "6. Remove files that are in the source directory, but are not in the destination directory and have equal files placed on other location in the destination. (Possibly time consuming operation)\n" +
                    "7. List all duplicates in a given directory. (Possibly time consuming operation)\n" +
                    "0. Exit.\n";
            selectOptionMsg = "Select an option (example: " + specialSequence + "): ";
            notFileErrorMsg = "'" + specialSequence + "' is not a file. File extension missing?";
            invalidOptionMsg = "Invalid option.\n";
            dir1Msg = "Directory 1: ";
            dir2Msg = "Directory 2: ";
            notFoundOnDir1Msg = "Not found on directory 1: " + specialSequence + "\n";
            notFoundOnDir2Msg = "Not found on directory 2: " + specialSequence + "\n";
            file1Msg = "File 1: ";
            file2Msg = "File 2: ";
            deletionErrorMsg = "It was not possible to remove '" + specialSequence + "'.\n";
            confirmationMsg = "Are you sure that you want to proceed? (Y/N): ";
            confirmInput = "Y";
            denyInput = "N";
            dirMsg = "Directory: ";
            generatedLoggerMsg = "Additional data will be logged on '" + specialSequence + "'.\n";
            foundMsg = "Found";
        }
    }

    public String getOptionsMsg() {
        return optionsMsg;
    }

    public String getSelectOptionMsg(int example) {
        return selectOptionMsg.replace(specialSequence, Integer.toString(example));
    }

    public String getNotFileErrorMsg(String filename) {
        return notFileErrorMsg.replace(specialSequence, filename);
    }

    public String getInvalidOptionMsg() {
        return invalidOptionMsg;
    }

    public String getDir1Msg() {
        return dir1Msg;
    }

    public String getDir2Msg() {
        return dir2Msg;
    }

    public String getNotFoundOnDir1Msg(int number) {
        return notFoundOnDir1Msg.replace(specialSequence, Integer.toString(number));
    }

    public String getNotFoundOnDir2Msg(int number) {
        return notFoundOnDir2Msg.replace(specialSequence, Integer.toString(number));
    }

    public String getFile1Msg() {
        return file1Msg;
    }

    public String getFile2Msg() {
        return file2Msg;
    }

    public String getDeletionErrorMsg(String name) {
        return deletionErrorMsg.replace(specialSequence, name);
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

    public String getDirMsg() {
        return dirMsg;
    }

    public String getGeneratedLoggerMsg(String loggerPath) {
        return generatedLoggerMsg.replace(specialSequence, loggerPath);
    }

    public String getFoundMsg() {
        return foundMsg;
    }
}
