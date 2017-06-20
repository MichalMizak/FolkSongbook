package sk.upjs.ics.folkjukebox.GUI.recycler.view;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sk.upjs.ics.folkjukebox.GUI.SongDetailActivity;

public class SimpleCursorRecyclerAdapter extends CursorRecyclerAdapter<SimpleCursorRecyclerAdapter.TextViewHolder> {

    private static ClickListener clickListener;

    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public SimpleCursorRecyclerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindViewHolder(TextViewHolder holder, Context context, Cursor cursor) {
        String title = cursor.getString(1);
        TextViewHolder vh = holder;
        vh.setText(title);
        vh.setCursor(cursor);
    }

    @Override
    public TextViewHolder createViewHolder(Context context, ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1,
                        parent, false);

        return new TextViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }


    public void remove(int adapterPosition) {
        // tu by sa vyhadzovalo aj z databazy
        notifyItemRemoved(adapterPosition);
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textView;
        private Cursor cursor;

        public TextViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            this.textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v, cursor);

            Log.d("RecyclerView", "CLICK!");
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v, cursor);
            return false;
        }

        public void setText(String text) {
            textView.setText(text);
        }

        public Cursor getCursor() {
            return cursor;
        }

        public void setCursor(Cursor cursor) {
            this.cursor = cursor;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v, Cursor cursor);

        void onItemLongClick(int position, View v, Cursor cursor);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        SimpleCursorRecyclerAdapter.clickListener = clickListener;
    }
}
