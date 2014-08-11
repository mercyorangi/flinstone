package com.wildlife.kws.adapter;

import com.kws.orangi.kenyawildlifeservice.R;
import com.wildlife.kws.interfaces.IAdapterViewInflater;
import com.wildlife.kws.model.CardItemDatamodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CardInflater implements IAdapterViewInflater<CardItemDatamodel>
{
	@Override
	public View inflate(final BaseInflaterAdapter<CardItemDatamodel> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card_home, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemDatamodel item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private ImageView image;
		private TextView caption;

		public ViewHolder(View rootView)
		{
			m_rootView = rootView;
			image = (ImageView) m_rootView.findViewById(R.id.iconhome);
			caption = (TextView) m_rootView.findViewById(R.id.captionhome);
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemDatamodel item)
		{
			image.setImageResource(item.getImage());
			caption.setText(item.getCaption());
		}
	}
}

