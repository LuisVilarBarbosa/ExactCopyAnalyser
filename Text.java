import java.util.Locale;

public class Text {
    private String specialSequence = "\0";
    private String language;
    private String optionsMsg;
    private String notFileErrorMsg;
    private String invalidOptionMsg;
    private String deletionErrorMsg;
    private String generatedLoggerMsg;
    private String foundMsg;

    public Text() {
        language = Locale.getDefault().getLanguage();

        if (language.equalsIgnoreCase("pt")) {
            optionsMsg = "\nOpções:\n"+
                    "1. Comparar o conteúdo de todos os ficheiros correspondentes em dois diretórios com a mesma estrutura.\n"+
                    "2. Comparar o conteúdo de dois ficheiros.\n"+
                    "3. Comparar todos os ficheiros correspondentes em dois diretórios com a mesma estrutura por tamanho e data de modificação.\n"+
                    "4. Comparar dois ficheiros por tamanho e data de modificação.\n"+
                    "5. Listar ficheiros que estão no diretório origem, mas não estão no diretório destino e têm ficheiros iguais noutra localização no destino. (Operação possivelmente demorada)\n"+
                    "6. Remover ficheiros que estão no diretório origem, mas não estão no diretório destino e têm ficheiros iguais noutra localização no destino. (Operação possivelmente demorada)\n"+
                    "7. Listar todos os duplicados num dado diretório. (Operação possivelmente demorada)\n"+
                    "0. Sair.\n";
            notFileErrorMsg = "'" + specialSequence + "' não é um ficheiro. Extensão do ficheiro em falta?";
            invalidOptionMsg = "Opção inválida.\n";
            deletionErrorMsg = "Não foi possível remover '" + specialSequence + "'.\n";
            generatedLoggerMsg = "Mais dados serão guardados em '" + specialSequence + "'.\n";
            foundMsg = "Found";
        } else {
            optionsMsg = "\nOptions:\n"+
                "1. Compare the content of all corresponding files in two directories with the same structure.\n"+
                   "2. Compare the content of two files.\n"+
                   "3. Compare all corresponding files in two directories with the same structure by size and modification date.\n"+
                   "4. Compare two files by size and modification date.\n"+
                   "5. List files that are in the source directory, but are not in the destination directory and have equal files placed on other location in the destination. (Possibly time consuming operation)\n"+
                   "6. Remove files that are in the source directory, but are not in the destination directory and have equal files placed on other location in the destination. (Possibly time consuming operation)\n"+
                   "7. List all duplicates in a given directory. (Possibly time consuming operation)\n"+
                   "0. Exit.\n";
            notFileErrorMsg = "'" + specialSequence + "' is not a file. File extension missing?";
        invalidOptionMsg = "Invalid option.\n";
            deletionErrorMsg = "It was not possible to remove '" + specialSequence + "'.\n";
            generatedLoggerMsg = "Extra data will be logged on '" + specialSequence + "'.\n";
            foundMsg = "Found";
        }
    }

    public String getOptionsMsg() {
        return optionsMsg;
    }

    public String getNotFileErrorMsg(String filename) {
        return notFileErrorMsg.replace(specialSequence,filename);
    }

    public String getInvalidOptionMsg() {
        return invalidOptionMsg;
    }

    public String getDeletionErrorMsg(String name) {
        return deletionErrorMsg.replace(specialSequence,name);
    }

    public String getGeneratedLoggerMsg(String loggerPath) {
        return generatedLoggerMsg.replace(specialSequence,loggerPath);
    }

    public String getFoundMsg() {
        return foundMsg;
    }
}
