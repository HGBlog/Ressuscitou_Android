package br.org.cn.ressuscitou;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterIndiceAlf extends BaseAdapter implements Filterable {
	private ArrayList<Canto> listData;
	private ArrayList<Canto> mOriginalValues; // Original Values
	private LayoutInflater layoutInflater;

	public AdapterIndiceAlf() {

	}

	public AdapterIndiceAlf(Context aContext, ArrayList<Canto> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(aContext);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_indice_alf, null);
			holder = new ViewHolder();
			holder.titulo = (TextView) convertView.findViewById(R.id.title);
			holder.img1 = (ImageView) convertView.findViewById(R.id.img1); 
			holder.img2 = (ImageView) convertView.findViewById(R.id.img2); 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.titulo.setText(listData.get(position).getTitulo());
		if(listData.get(position).getCategoria() == 1)holder.img2.setImageResource(R.drawable.dotwhite);
		if(listData.get(position).getCategoria() == 2)holder.img2.setImageResource(R.drawable.dotblue);
		if(listData.get(position).getCategoria() == 3)holder.img2.setImageResource(R.drawable.dotgreen);
		if(listData.get(position).getCategoria() == 4)holder.img2.setImageResource(R.drawable.dotbeige);
		
		if(listData.get(position).getUrl().toString().isEmpty()) {
			holder.img1.setImageResource(R.drawable.aud_n);
		}else {
			holder.img1.setImageResource(R.drawable.aud_y);
		}

		return convertView;
	}
	static class ViewHolder {
		TextView titulo;
		ImageView img1;
		ImageView img2;
	}
	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) {
				listData = (ArrayList<Canto>) results.values; // has the filtered values
				notifyDataSetChanged();  // notifies the data with new filtered values
			}

			@SuppressLint("DefaultLocale")
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
				List<Canto> FilteredArrList = new ArrayList<Canto>();

				if (mOriginalValues == null) {
					mOriginalValues = new ArrayList<Canto>(listData); // saves the original data in mOriginalValues
				}

				if (constraint == null || constraint.length() == 0) {
					// set the Original result to return
					results.count = mOriginalValues.size();
					results.values = mOriginalValues;
				} else {
					constraint = constraint.toString().toLowerCase();
					for (int i = 0; i < mOriginalValues.size(); i++) {
						Canto data = mOriginalValues.get(i);

						String strC = constraint.toString();
						strC = Normalizer.normalize(strC, Normalizer.Form.NFD);
						strC = strC.replaceAll("[^\\p{ASCII}]", "");
						strC = strC.replaceAll("[-,.;!?]", "");
						strC = strC.replaceAll("[ ]", "");

						if (data.getConteudo().contains(strC)) {
							FilteredArrList.add(data);
						}
					}
					// set the Filtered result to return
					results.count = FilteredArrList.size();
					results.values = FilteredArrList;
				}
				return results;
			}
		};
		return filter;
	}

}
