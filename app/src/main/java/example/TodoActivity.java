package example;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lgvalle.com.fluxtodo.R;
import ru.qixi.redux.HandlerDispatcher;


public class TodoActivity extends AppCompatActivity implements TodoView {

    private TodoPresenter       presenter;
    private RecyclerView        recyclerView;
    private EditText            mainInput;
    private ViewGroup           mainLayout;
    private TodoRecyclerAdapter listAdapter;
    private CheckBox            mainCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.presenter = new TodoPresenter(HandlerDispatcher.get());
        setupView();
    }

    private void setupView() {
        presenter.attachView(this);
        mainLayout = findViewById(R.id.main_layout);
        mainInput = findViewById(R.id.main_input);

        Button mainAdd = findViewById(R.id.main_add);
        mainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickAdd(getInputText());
            }
        });
        mainCheck = findViewById(R.id.main_checkbox);
        mainCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickCheckAll();
            }
        });
        Button mainClearCompleted = findViewById(R.id.main_clear_completed);
        mainClearCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickClearCompleted();
            }
        });

        recyclerView = findViewById(R.id.main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        listAdapter = new TodoRecyclerAdapter(presenter.getActionsCreator());
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void updateUI(List<TodoData> todos) {
        listAdapter.updateItems(todos);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void resetMainInput() {
        mainInput.setText("");
    }

    @Override
    public void resetMainCheck() {
        if (mainCheck.isChecked()) {
            mainCheck.setChecked(false);
        }
    }

    @Override
    public void showSnackbar(String text) {
        Snackbar snackbar = Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickUndo();
            }
        });
        snackbar.show();
    }

    private String getInputText() {
        return mainInput.getText().toString();
    }

}