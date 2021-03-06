package ui;

import java.util.Locale;

public class Text {
    private static final String specialSequence = "\0";
    private String language;
    private String optionsMsg;
    private String selectOptionMsg;
    private String notFileErrorMsg;
    private String invalidOptionMsg;
    private String notDirErrorMsg;
    private String fileReadErrorMsg;
    private String dir1Msg;
    private String dir2Msg;
    private String dir2FilesWithoutCorrespondentOnDir1Msg;
    private String dir1FilesWithoutCorrespondentOnDir2Msg;
    private String notEqualCorrespondentsMsg;
    private String file1Msg;
    private String file2Msg;
    private String equalFilesMsg;
    private String notEqualFilesMsg;
    private String dir1FilesWithoutCorrespondentInDir2ButWithCopiesThereMsg;
    private String deletionErrorMsg;
    private String confirmationMsg;
    private String confirmInput;
    private String denyInput;
    private String deletingFilesMsg;
    private String dirMsg;
    private String duplicateFilesMsg;
    private String loggerStartMsg;
    private String generatedLoggerMsg;
    private String progressMsg;
    private String remainingTimeMsg;
    private String calculatingMsg;
    private String invalidArgMsg;
    private String dir1FilesWithCopiesSomewhereInDir2Msg;
    private String dir1FilesWithoutCopiesAnywhereInDir2Msg;
    private String threadInterruptedExceptionMsg;
    private String foldersWithoutFilesAsDescendantsMsg;
    private String deletingFoldersMsg;
    private String dir1FilesWithoutCorrespondentInDir2AndWithoutCopiesThereMsg;

    public Text() {
        language = Locale.getDefault().getLanguage();

        if (language.equalsIgnoreCase("pt")) {
            optionsMsg = "\nOpções:\n" +
                    " 1-Comparar todos os ficheiros correspondentes em dois diretórios com a mesma estrutura de ficheiros (modo 1).\n" +
                    " 2-Comparar todos os ficheiros correspondentes em dois diretórios com a mesma estrutura de ficheiros (modo 2).\n" +
                    " 3-Comparar todos os ficheiros correspondentes em dois diretórios com a mesma estrutura de ficheiros (modo 3).\n" +
                    " 4-Comparar dois ficheiros (modo 1).\n" +
                    " 5-Comparar dois ficheiros (modo 2).\n" +
                    " 6-Comparar dois ficheiros (modo 3).\n" +
                    " 7-Listar ficheiros do diretório 1 sem correspondente no diretório 2, mas com cópias noutra localização no diretório 2 (modo 2).\n" +
                    " 8-Listar ficheiros do diretório 1 sem correspondente no diretório 2, mas com cópias noutra localização no diretório 2 (modo 3).\n" +
                    " 9-Remover ficheiros do diretório 1 sem correspondente no diretório 2, mas com cópias noutra localização no diretório 2 (não remove pastas vazias) (modo 2).\n" +
                    "10-Remover ficheiros do diretório 1 sem correspondente no diretório 2, mas com cópias noutra localização no diretório 2 (não remove pastas vazias) (modo 3).\n" +
                    "11-Listar todos os duplicados num dado diretório (modo 2).\n" +
                    "12-Listar todos os duplicados num dado diretório (modo 3).\n" +
                    "13-Listar ficheiros do diretório 1 com cópias algures no diretório 2 (modo 2).\n" +
                    "14-Listar ficheiros do diretório 1 com cópias algures no diretório 2 (modo 3).\n" +
                    "15-Listar ficheiros do diretório 1 sem cópias em nenhum local no diretório 2 (modo 2).\n" +
                    "16-Listar ficheiros do diretório 1 sem cópias em nenhum local no diretório 2 (modo 3).\n" +
                    "17-Listar pastas sem ficheiros como descendentes.\n" +
                    "18-Remover pastas sem ficheiros como descendentes.\n" +
                    "19-Listar ficheiros do diretório 1 sem correspondente no diretório 2 e sem cópias noutra localização no diretório 2 (modo 2).\n" +
                    "20-Listar ficheiros do diretório 1 sem correspondente no diretório 2 e sem cópias noutra localização no diretório 2 (modo 3).\n" +
                    " 0-Sair.\n" +
                    "Modo 1: Comparação por tamanho e data de modificação.\n" +
                    "Modo 2: Comparação por tamanho e conteúdo. A análise do conteúdo pode ser demorada.\n" +
                    "Modo 3: Comparação por tamanho, data de modificação e conteúdo. A análise do conteúdo pode ser demorada.\n";
            selectOptionMsg = "\nSeleciona uma opção (exemplo: " + specialSequence + "): ";
            notFileErrorMsg = "'" + specialSequence + "' não é um ficheiro. Extensão do ficheiro em falta?\n";
            invalidOptionMsg = "Opção inválida.\n";
            notDirErrorMsg = "'" + specialSequence + "' não é um diretório.\n";
            fileReadErrorMsg = "Ocorreu um erro ao ler os dados dos ficheiros.\n";
            dir1Msg = "Diretório 1: ";
            dir2Msg = "Diretório 2: ";
            dir2FilesWithoutCorrespondentOnDir1Msg = "Ficheiros do diretório 2 sem correspondente no diretório 1: " + specialSequence + "\n";
            dir1FilesWithoutCorrespondentOnDir2Msg = "Ficheiros do diretório 1 sem correspondente no diretório 2: " + specialSequence + "\n";
            notEqualCorrespondentsMsg = "Casos de ficheiros correspondentes não iguais: " + specialSequence + "\n";
            file1Msg = "Ficheiro 1: ";
            file2Msg = "Ficheiro 2: ";
            equalFilesMsg = "Os ficheiros são iguais.\n";
            notEqualFilesMsg = "Os ficheiros não são iguais.\n";
            dir1FilesWithoutCorrespondentInDir2ButWithCopiesThereMsg = "Ficheiros do diretório 1 sem correspondente no diretório 2, mas com cópias lá: " + specialSequence + "\n";
            deletionErrorMsg = "Não foi possível eliminar '" + specialSequence + "'.\n";
            confirmationMsg = "Tem a certeza que deseja continuar? (S/N): ";
            confirmInput = "S";
            denyInput = "N";
            deletingFilesMsg = "Eliminando os ficheiros...\n";
            dirMsg = "Diretório: ";
            duplicateFilesMsg = "Ficheiros duplicados: " + specialSequence + " = " + specialSequence + " bytes\n";
            loggerStartMsg = "Dados gerados pelo FilesStructureAnalyser serão guardados abaixo.\n\n";
            generatedLoggerMsg = "Dados adicionais serão guardados em '" + specialSequence + "'.\n";
            progressMsg = specialSequence + " B / " + specialSequence + " B = " + specialSequence + "% Encontrado(s): " + specialSequence + " Tempo restante: " + specialSequence;
            remainingTimeMsg = specialSequence + "h" + specialSequence + "m" + specialSequence + "s";
            calculatingMsg = "calculando...";
            invalidArgMsg = "Argumento inválido.";
            dir1FilesWithCopiesSomewhereInDir2Msg = "Ficheiros do diretório 1 com cópias no diretório 2: " + specialSequence + "\n";
            dir1FilesWithoutCopiesAnywhereInDir2Msg = "Ficheiros do diretório 1 sem cópias no diretório 2: " + specialSequence + "\n";
            threadInterruptedExceptionMsg = "Um thread foi interrompido de forma anómala, gerando uma exceção.\n";
            foldersWithoutFilesAsDescendantsMsg = "Pastas sem ficheiros como descendentes: " + specialSequence + "\n";
            deletingFoldersMsg = "Eliminando as pastas...\n";
            dir1FilesWithoutCorrespondentInDir2AndWithoutCopiesThereMsg = "Ficheiros do diretório 1 sem correspondente no diretório 2 e sem cópias lá: " + specialSequence + "\n";
        } else {
            optionsMsg = "Options:\n" +
                    " 1-Compare all corresponding files in two directories with the same structure of files (mode 1).\n" +
                    " 2-Compare all corresponding files in two directories with the same structure of files (mode 2).\n" +
                    " 3-Compare all corresponding files in two directories with the same structure of files (mode 3).\n" +
                    " 4-Compare two files (mode 1).\n" +
                    " 5-Compare two files (mode 2).\n" +
                    " 6-Compare two files (mode 3).\n" +
                    " 7-List files from directory 1 without correspondent in directory 2, but with copies on other location in directory 2 (mode 2).\n" +
                    " 8-List files from directory 1 without correspondent in directory 2, but with copies on other location in directory 2 (mode 3).\n" +
                    " 9-Remove files from directory 1 without correspondent in directory 2, but with copies on other location in directory 2 (does not remove empty folders) (mode 2).\n" +
                    "10-Remove files from directory 1 without correspondent in directory 2, but with copies on other location in directory 2 (does not remove empty folders) (mode 3).\n" +
                    "11-List all duplicates in a given directory (mode 2).\n" +
                    "12-List all duplicates in a given directory (mode 3).\n" +
                    "13-List files from directory 1 with copies somewhere in directory 2 (mode 2).\n" +
                    "14-List files from directory 1 with copies somewhere in directory 2 (mode 3).\n" +
                    "15-List files from directory 1 without copies anywhere in directory 2 (mode 2).\n" +
                    "16-List files from directory 1 without copies anywhere in directory 2 (mode 3).\n" +
                    "17-List folders without files as descendants.\n" +
                    "18-Remove folders without files as descendants.\n" +
                    "19-List files from directory 1 without correspondent in directory 2 and without copies on other location in directory 2 (mode 2).\n" +
                    "20-List files from directory 1 without correspondent in directory 2 and without copies on other location in directory 2 (mode 3).\n" +
                    " 0-Exit.\n" +
                    "Mode 1: Comparison by size and modification date.\n" +
                    "Mode 2: Comparison by size and content. Content analysis can be time-consuming.\n" +
                    "Mode 3: Comparison by size, modification date and content. Content analysis can be time-consuming.\n";
            selectOptionMsg = "\nSelect an option (example: " + specialSequence + "): ";
            notFileErrorMsg = "'" + specialSequence + "' is not a file. objects. File extension missing?\n";
            invalidOptionMsg = "Invalid option.\n";
            notDirErrorMsg = "'" + specialSequence + "' is not a directory.\n";
            fileReadErrorMsg = "An error occurred reading the data from the files.\n";
            dir1Msg = "Directory 1: ";
            dir2Msg = "Directory 2: ";
            dir2FilesWithoutCorrespondentOnDir1Msg = "Files from directory 2 without correspondent on directory 1: " + specialSequence + "\n";
            dir1FilesWithoutCorrespondentOnDir2Msg = "Files from directory 1 without correspondent on directory 2: " + specialSequence + "\n";
            notEqualCorrespondentsMsg = "Cases of not equal correspondent files: " + specialSequence + "\n";
            file1Msg = "File 1: ";
            file2Msg = "File 2: ";
            equalFilesMsg = "The files are equal.\n";
            notEqualFilesMsg = "The files are not equal.\n";
            dir1FilesWithoutCorrespondentInDir2ButWithCopiesThereMsg = "Files from directory 1 without correspondent in directory 2 but with copies there: " + specialSequence + "\n";
            deletionErrorMsg = "It was not possible to delete '" + specialSequence + "'.\n";
            confirmationMsg = "Are you sure that you want to proceed? (Y/N): ";
            confirmInput = "Y";
            denyInput = "N";
            deletingFilesMsg = "Deleting files...\n";
            dirMsg = "Directory: ";
            duplicateFilesMsg = "Duplicate files: " + specialSequence + " = " + specialSequence + " bytes\n";
            loggerStartMsg = "Data generated by FilesStructureAnalyser will be stored below.\n\n";
            generatedLoggerMsg = "Additional data will be logged on '" + specialSequence + "'.\n";
            progressMsg = specialSequence + " B / " + specialSequence + " B = " + specialSequence + "% Found: " + specialSequence + " Remaining time: " + specialSequence;
            remainingTimeMsg = specialSequence + "h" + specialSequence + "m" + specialSequence + "s";
            calculatingMsg = "calculating...";
            invalidArgMsg = "Invalid argument.";
            dir1FilesWithCopiesSomewhereInDir2Msg = "Files from directory 1 with copies in directory 2: " + specialSequence + "\n";
            dir1FilesWithoutCopiesAnywhereInDir2Msg = "Files from directory 1 without copies in directory 2: " + specialSequence + "\n";
            threadInterruptedExceptionMsg = "A thread has been interrupted anomalously, generating an exception.\n";
            foldersWithoutFilesAsDescendantsMsg = "Folders without files as descendants: " + specialSequence + "\n";
            deletingFoldersMsg = "Deleting folders...\n";
            dir1FilesWithoutCorrespondentInDir2AndWithoutCopiesThereMsg = "Files from directory 1 without correspondent in directory 2 and without copies there: " + specialSequence + "\n";
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

    public String getDir2FilesWithoutCorrespondentOnDir1Msg(int number) {
        return dir2FilesWithoutCorrespondentOnDir1Msg.replaceFirst(specialSequence, formatInteger(number));
    }

    public String getDir1FilesWithoutCorrespondentOnDir2Msg(int number) {
        return dir1FilesWithoutCorrespondentOnDir2Msg.replaceFirst(specialSequence, formatInteger(number));
    }

    public String getNotEqualCorrespondentsMsg(int number) {
        return notEqualCorrespondentsMsg.replaceFirst(specialSequence, formatInteger(number));
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

    public String getDir1FilesWithoutCorrespondentInDir2ButWithCopiesThereMsg(int number) {
        return dir1FilesWithoutCorrespondentInDir2ButWithCopiesThereMsg.replaceFirst(specialSequence, formatInteger(number));
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
        return duplicateFilesMsg.replaceFirst(specialSequence, formatInteger(number))
                .replaceFirst(specialSequence, formatInteger(size));
    }

    public String getLoggerStartMsg() {
        return loggerStartMsg;
    }

    public String getGeneratedLoggerMsg(String loggerPath) {
        return generatedLoggerMsg.replaceFirst(specialSequence, loggerPath);
    }

    public String getProgressMsg(long done, long total, double percentage, int found, String remainingTime) {
        return progressMsg.replaceFirst(specialSequence, formatInteger(done))
                .replaceFirst(specialSequence, formatInteger(total))
                .replaceFirst(specialSequence, String.format("%.3f", percentage))
                .replaceFirst(specialSequence, formatInteger(found))
                .replaceFirst(specialSequence, remainingTime);
    }

    public String getRemainingTimeMsg(long hours, long minutes, long seconds) {
        return remainingTimeMsg.replaceFirst(specialSequence, Long.toString(hours))
                .replaceFirst(specialSequence, String.format("%02d", minutes))
                .replaceFirst(specialSequence, String.format("%02d", seconds));
    }

    public String getCalculatingMsg() {
        return calculatingMsg;
    }

    public String getInvalidArgMsg() {
        return invalidArgMsg;
    }

    public String getDir1FilesWithCopiesSomewhereInDir2Msg(int number) {
        return dir1FilesWithCopiesSomewhereInDir2Msg.replaceFirst(specialSequence, formatInteger(number));
    }

    public String getDir1FilesWithoutCopiesAnywhereInDir2Msg(int number) {
        return dir1FilesWithoutCopiesAnywhereInDir2Msg.replaceFirst(specialSequence, formatInteger(number));
    }

    public String getThreadInterruptedExceptionMsg() {
        return threadInterruptedExceptionMsg;
    }

    public String getFoldersWithoutFilesAsDescendantsMsg(int number) {
        return foldersWithoutFilesAsDescendantsMsg.replaceFirst(specialSequence, formatInteger(number));
    }

    public String getDeletingFoldersMsg() {
        return deletingFoldersMsg;
    }

    public String getDir1FilesWithoutCorrespondentInDir2AndWithoutCopiesThereMsg(int number) {
        return dir1FilesWithoutCorrespondentInDir2AndWithoutCopiesThereMsg.replaceFirst(specialSequence, formatInteger(number));
    }

    private static String formatInteger(long number) {
        String str = Long.toString(number);
        for (int i = str.length() - 3; i > 0; i -= 3)
            str = str.substring(0, i).concat(" ").concat(str.substring(i, str.length()));
        return str;
    }
}
