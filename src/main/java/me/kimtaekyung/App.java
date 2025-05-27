package me.kimtaekyung;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    List<WiseSaying> wiseSayings = new ArrayList<>();
    int lastId = 0;

    void run(){

        System.out.println("== 명언 앱 ==");

        while(true){
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("등록")) {
                registerWiseSaying();
            } else if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("목록")) {
                showWiseSaying();
            } else if (cmd.startsWith("삭제")) {
                deleteWiseSaying(cmd);
            } else if (cmd.startsWith("수정")) {
                modifyWiseSaying(cmd);
            }
        }
    }

    void registerWiseSaying(){
        System.out.print("명언 : ");
        String wiseSayingContent = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String wiseSayingAuthor = scanner.nextLine().trim();

        register(++lastId,wiseSayingContent,wiseSayingAuthor);
    }

    void register(int lastId, String wiseSayingContent, String wiseSayingAuthor) {
        WiseSaying wiseSaying = new WiseSaying(lastId, wiseSayingContent, wiseSayingAuthor);
        wiseSayings.add(wiseSaying);

        System.out.println("%d번째 명언이 등록되었습니다.".formatted(lastId));
    }

    void showWiseSaying(){
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");

        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            System.out.println("%d / %s / %s".formatted(
                    wiseSaying.getId(),
                    wiseSaying.getAuthor(),
                    wiseSaying.getContent()
            ));
        }
    }

    void deleteWiseSaying(String cmd){
        try{
            int deleteId = getId(cmd);
            WiseSaying wiseSaying = findById(deleteId);
            ExistById(wiseSaying,deleteId);

            wiseSayings.remove(findById(deleteId));

            System.out.println("%d번째 명언이 삭제되었습니다.".formatted(deleteId));
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    void modifyWiseSaying(String cmd) {
        try {
            int updateId = getId(cmd);

            WiseSaying wiseSaying = findById(updateId);
            ExistById(wiseSaying,updateId);

            modify(wiseSaying);

            System.out.println("%d번째 명언이 수정되었습니다.".formatted(updateId));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    void ExistById(WiseSaying wiseSaying,int id) {
        if (wiseSaying == null) {
            throw new IllegalArgumentException("%d번째 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    void modify(WiseSaying wiseSaying){
        System.out.println("명언(기존) : %s".formatted(wiseSaying.getContent()));
        System.out.print("명언 : ");
        String content=scanner.nextLine().trim();
        System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        wiseSaying.setAuthor(author);
        wiseSaying.setContent(content);
    }

    int getId(String cmd) {
        String[] cmdAndId = cmd.split("=", 2);
        if (cmdAndId.length < 2 || cmdAndId[1] == null) {
            throw new IllegalArgumentException("id를 입력해주세요.");
        }
        return Integer.parseInt(cmdAndId[1]);
    }

    WiseSaying findById(int id){

        for (WiseSaying w : wiseSayings) {
            if (id == w.getId()) {
                return w;
            }
        }
        return null;
    }
}
