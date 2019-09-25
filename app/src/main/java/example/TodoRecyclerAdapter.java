package example;

import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import lgvalle.com.fluxtodo.R;


public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {

    private ActionsCreator actionsCreator;
    private List<TodoData> todos;

    public TodoRecyclerAdapter(ActionsCreator actionsCreator) {
        this.todos = new ArrayList<>();
        this.actionsCreator = actionsCreator;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row_layout, parent, false);
        return new ViewHolder(view, actionsCreator);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bindView(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void updateItems(List<TodoData> items) {
        this.todos.clear();
        this.todos.addAll(items);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView todoText;
        public CheckBox todoCheck;
        public Button   todoDelete;

        private ActionsCreator actionsCreator;

        public ViewHolder(View view, ActionsCreator actionsCreator) {
            super(view);
            this.actionsCreator = actionsCreator;
            todoText = (TextView) view.findViewById(R.id.row_text);
            todoCheck = (CheckBox) view.findViewById(R.id.row_checkbox);
            todoDelete = (Button) view.findViewById(R.id.row_delete);
        }

        public void bindView(final TodoData todo) {
            if (todo.isComplete()) {
                SpannableString spanString = new SpannableString(todo.getText());
                spanString.setSpan(new StrikethroughSpan(), 0, spanString.length(), 0);
                todoText.setText(spanString);
            } else {
                todoText.setText(todo.getText());
            }

            todoCheck.setChecked(todo.isComplete());
            todoCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionsCreator.toggleComplete(todo);
                }
            });

            todoDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionsCreator.destroy(todo.getId());
                }
            });
        }
    }
}
