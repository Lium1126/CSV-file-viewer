package jp.ac.ait.k19061.section10;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVFileViewer {
    public static void main(String[] args) throws IOException {

        // 1.ユーザからファイル名入力を受け付ける
        System.out.print("ファイルパスを入力してください: ");
        Scanner sc = new Scanner(System.in);
        String filepath = sc.nextLine();

        // 2.ファイルが適切か判定する
        boolean isProperFile = true;

        // 2-1.ファイルが存在するか
        if (!Files.exists(Path.of(filepath))) {
            System.out.println("ファイルがありません");
            isProperFile = false;
        }

        // 2-2.ディレクトリでないか
        else if (Files.isDirectory(Path.of(filepath))) {
            System.out.println("ディレクトリが指定されています");
            isProperFile = false;
        }

        // 2-3.ファイルが読み取り可能か
        else if (!Files.isReadable(Path.of(filepath))) {
            System.out.println("ファイルを読み取れません");
            isProperFile = false;
        }

        // 2-4.シンボリックリンクでないか
        else if (Files.isSymbolicLink(Path.of(filepath))) {
            System.out.println("入力されたファイルはシンボリックリンクです");
            isProperFile = false;
        }

        // 2-5.隠しファイルでないか
        else if (Files.isHidden(Path.of(filepath))) {
            System.out.println("入力されたファイルは隠しファイルです");
            isProperFile = false;
        }

        // 2-6.csvファイルか
        else if (!filepath.substring(filepath.lastIndexOf(".")).equals(".csv")) {
            System.out.println("CSVファイルを指定してください");
            isProperFile = false;
        }

        // 適切でないファイルの場合はプログラムを終了する
        if (!isProperFile) {
            sc.close();
            System.exit(-1);
        }

        // 3.ファイルの内容を読み込んで画面に表示
        int lineNum = 1;  // 行番号
        try (Scanner sc2 = new Scanner(Files.newBufferedReader(Path.of(filepath),
                Charset.forName("UTF-8")))) {
            // 3-1.1行ずつ読み込む
            while (sc2.hasNextLine()) {
                String csv_line = sc2.nextLine();

                // 3-2.カンマで区切ってリスト化
                List<String> data = new ArrayList<>(Arrays.asList(csv_line.split(",", -1)));

                // 3-3.dataの内容を画面表示
                System.out.print(lineNum + ":\t");
                for (ListIterator<String> itr = data.listIterator();itr.hasNext();) {
                    int i = itr.nextIndex();  // インデックス
                    String s = itr.next();    // 表示する内容

                    System.out.print((i + 1) + ":" + s);
                    if (itr.hasNext()) {
                        System.out.print("\t");
                    } else {
                        System.out.print("\n");
                    }
                }

                // 3-4.行番号をインクリメント
                lineNum += 1;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }  // try-with-resources構文によりclose不要
    }
}
