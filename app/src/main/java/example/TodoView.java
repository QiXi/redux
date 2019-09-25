package example;

import java.util.List;


interface TodoView {

    void updateUI(List<TodoData> todos);

    void resetMainInput();

    void resetMainCheck();

    void showSnackbar(String text);

}