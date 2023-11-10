package com.notesplus.KrishSatasiya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class NotesListActivity extends  AppCompatActivity  { 
	
	
	private FloatingActionButton _fab;
	private double Position = 0;
	private boolean Selected = false;
	private double Selected_Length = 0;
	private boolean IsSelectAll = false;
	
	private ArrayList<HashMap<String, Object>> Notes_List = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> temp_maplist = new ArrayList<>();
	
	private LinearLayout toolbar;
	private LinearLayout main;
	private ImageView back_img;
	private TextView toolbar_txt;
	private ImageView select_all_img;
	private ListView notes_list;
	private LinearLayout linear1;
	
	private Intent To_Note_View = new Intent();
	private SharedPreferences Settings;
	private SharedPreferences AllNotes;
	private AlertDialog.Builder Delete_Dialog;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.notes_list);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		toolbar = (LinearLayout) findViewById(R.id.toolbar);
		main = (LinearLayout) findViewById(R.id.main);
		back_img = (ImageView) findViewById(R.id.back_img);
		toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
		select_all_img = (ImageView) findViewById(R.id.select_all_img);
		notes_list = (ListView) findViewById(R.id.notes_list);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		Settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		AllNotes = getSharedPreferences("All Notes", Activity.MODE_PRIVATE);
		Delete_Dialog = new AlertDialog.Builder(this);
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		select_all_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_SelectAll();
			}
		});
		
		notes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (Selected) {
					if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Select").toString().equals("True")) {
						Notes_List.get((int)(Notes_List.size() - 1) - _position).put("Select", "False");
						Selected_Length--;
						IsSelectAll = false;
					}
					else {
						Notes_List.get((int)(Notes_List.size() - 1) - _position).put("Select", "True");
						Selected_Length++;
					}
					_Refresh(Notes_List);
					_Toolbar(Selected_Length);
					if (Selected_Length == 0) {
						IsSelectAll = false;
						select_all_img.setEnabled(true);
						select_all_img.setVisibility(View.INVISIBLE);
						toolbar_txt.setText("Your Notes");
						_Fab(false);
						Selected = false;
						((BaseAdapter)notes_list.getAdapter()).notifyDataSetChanged();
					}
				}
				else {
					To_Note_View.setAction(Intent.ACTION_VIEW);
					To_Note_View.setClass(getApplicationContext(), NoteViewActivity.class);
					To_Note_View.putExtra("Position", String.valueOf((long)((Notes_List.size() - 1) - _position)));
					startActivity(To_Note_View);
					finish();
				}
			}
		});
		
		notes_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (Selected) {
					
				}
				else {
					Selected = true;
					Notes_List.get((int)(Notes_List.size() - 1) - _position).put("Select", "True");
					_Refresh(Notes_List);
					_Fab(true);
					Selected_Length++;
					_Toolbar(Selected_Length);
					select_all_img.setVisibility(View.VISIBLE);
					select_all_img.setEnabled(true);
				}
				return true;
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Delete_Dialog.setTitle("Delete ?");
				Delete_Dialog.setMessage("Are You Sure To Delete ".concat(String.valueOf((long)(Selected_Length)).concat(" Selected Items ? This Action Cannot Be Undone.")));
				Delete_Dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						Selected = false;
						Position = Notes_List.size() - 1;
						while(true) {
							if (Position == -1) {
								break;
							}
							else {
								if (Notes_List.get((int)Position).get("Select").toString().equals("True")) {
									Notes_List.remove((int)(Position));
								}
								Position--;
							}
						}
						_Refresh(Notes_List);
						Selected_Length = 0;
						toolbar_txt.setText("Your Notes");
						_Fab(false);
						select_all_img.setVisibility(View.INVISIBLE);
						_SaveData();
					}
				});
				Delete_Dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						_UnSelectAll();
					}
				});
				Delete_Dialog.create().show();
			}
		});
	}
	
	private void initializeLogic() {
		toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		_removeScollBar(notes_list);
		_GetSettingsData();
		_Fab(false);
		select_all_img.setVisibility(View.INVISIBLE);
		select_all_img.setEnabled(false);
		if (!getIntent().getStringExtra("Data").equals("")) {
			Notes_List = new Gson().fromJson(getIntent().getStringExtra("Data"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			notes_list.setAdapter(new Notes_listAdapter(Notes_List));
			((BaseAdapter)notes_list.getAdapter()).notifyDataSetChanged();
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (Selected) {
			_UnSelectAll();
		}
		else {
			finish();
		}
	}
	public void _SetRadiusToView (final View _view, final double _radius, final String _Colour) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(); gd.setColor(Color.parseColor(_Colour)); gd.setCornerRadius((int)_radius); _view.setBackground(gd);
	}
	
	
	public void _SetShadowOfView (final View _view, final double _value) {
		_view.setElevation((float)_value);
	}
	
	
	public void _SX_CornerRadius_4 (final View _view, final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		
		gd.setColor(Color.parseColor(_color1));
		
		gd.setStroke((int)_str, Color.parseColor(_color2));
		
		gd.setCornerRadii(new float[]{(int)_n1,(int)_n1,(int)_n2,(int)_n2,(int)_n3,(int)_n3,(int)_n4,(int)_n4});
		
		_view.setBackground(gd);
		
		_view.setElevation(4);
	}
	
	
	public void _Add (final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _removeScollBar (final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _GetSettingsData () {
		if (Settings.getString("Text Size", "").equals("Small")) {
			
		}
		else {
			if (Settings.getString("Text Size", "").equals("Small")) {
				_textSize(toolbar_txt, 20);
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					_textSize(toolbar_txt, 22);
				}
				else {
					
				}
			}
		}
		if (Settings.getString("Theme", "").equals("Default")) {
			Window w = NotesListActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			_Add("#3770FD", back_img);
			_Add("#3770FD", select_all_img);
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				Window w = NotesListActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#263238"));
				toolbar.setBackgroundColor(0xFF263238);
				toolbar_txt.setTextColor(0xFFFFFFFF);
				main.setBackgroundColor(0xFF263238);
				_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#263238")));
				_Add("#FFFFFF", back_img);
				_Add("#FFFFFF", select_all_img);
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					Window w = NotesListActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF"));
					main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					toolbar.setBackgroundColor(0xFFFFFFFF);
					toolbar_txt.setTextColor(0xFF607D8B);
					main.setBackgroundColor(0xFFFFFFFF);
					_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#607D8B")));
					_Add("#607D8B", back_img);
					_Add("#607D8B", select_all_img);
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						Window w = NotesListActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF"));
						main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						toolbar.setBackgroundColor(0xFFFFFFFF);
						toolbar_txt.setTextColor(0xFFFF5722);
						main.setBackgroundColor(0xFFFFFFFF);
						_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF5722")));
						_Add("#FF5722", back_img);
						_Add("#FF5722", select_all_img);
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							Window w = NotesListActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF"));
							main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
							toolbar.setBackgroundColor(0xFFFFFFFF);
							toolbar_txt.setTextColor(0xFF3F51B5);
							main.setBackgroundColor(0xFFFFFFFF);
							_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#3F51B5")));
							_Add("#3F51B5", back_img);
							_Add("#3F51B5", select_all_img);
						}
						else {
							
						}
					}
				}
			}
		}
	}
	
	
	public void _textSize (final TextView _TextView1, final double _Size) {
		int j = (int) _Size;
		_TextView1.setTextSize(j);
	}
	
	
	public void _Fab (final boolean _Visibility) {
		if (_Visibility) {
			_fab.show();
		}
		else {
			_fab.hide();
		}
	}
	
	
	public void _Toolbar (final double _Length) {
		toolbar_txt.setText(String.valueOf((long)(_Length)).concat("/".concat(String.valueOf((long)(Notes_List.size())).concat(" Selected"))));
	}
	
	
	public void _UnSelectAll () {
		Selected = false;
		Selected_Length = 0;
		toolbar_txt.setText("Your Notes");
		select_all_img.setVisibility(View.INVISIBLE);
		select_all_img.setEnabled(false);
		IsSelectAll = false;
		Position = 0;
		for(int _repeat14 = 0; _repeat14 < (int)(Notes_List.size()); _repeat14++) {
			if (Notes_List.get((int)Position).get("Select").toString().equals("True")) {
				Notes_List.get((int)Position).put("Select", "False");
			}
			Position++;
		}
		_Refresh(Notes_List);
		_Fab(false);
	}
	
	
	public void _Refresh (final ArrayList<HashMap<String, Object>> _ListMap) {
		Parcelable state =
		notes_list.onSaveInstanceState();
		notes_list.setAdapter(new Notes_listAdapter(_ListMap));
		((BaseAdapter)notes_list.getAdapter()).notifyDataSetChanged();
		notes_list.onRestoreInstanceState(state);
	}
	
	
	public void _SelectAll () {
		if (IsSelectAll) {
			_UnSelectAll();
			IsSelectAll = false;
		}
		else {
			IsSelectAll = true;
			Position = 0;
			for(int _repeat13 = 0; _repeat13 < (int)(Notes_List.size()); _repeat13++) {
				if (Notes_List.get((int)Position).get("Select").toString().equals("False")) {
					Notes_List.get((int)Position).put("Select", "True");
					Selected_Length++;
				}
				Position++;
				_Toolbar(Selected_Length);
			}
			_Refresh(Notes_List);
		}
	}
	
	
	public void _SaveData () {
		temp_maplist = new Gson().fromJson(AllNotes.getString("Notes", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		if (Notes_List.size() == 0) {
			if (getIntent().getStringExtra("Type").equals("All")) {
				AllNotes.edit().putString("Notes", new Gson().toJson(Notes_List)).commit();
			}
			else {
				if (getIntent().getStringExtra("Type").equals("Important")) {
					Position = temp_maplist.size() - 1;
					for(int _repeat24 = 0; _repeat24 < (int)(temp_maplist.size()); _repeat24++) {
						if (temp_maplist.get((int)Position).get("Important").toString().equals("True")) {
							temp_maplist.remove((int)(Position));
						}
						Position--;
					}
					AllNotes.edit().putString("Notes", new Gson().toJson(temp_maplist)).commit();
				}
			}
		}
		else {
			AllNotes.edit().putString("Notes", new Gson().toJson(Notes_List)).commit();
		}
	}
	
	
	public class Notes_listAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Notes_listAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.notes, null);
			}
			
			final LinearLayout bg = (LinearLayout) _view.findViewById(R.id.bg);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout side_lin = (LinearLayout) _view.findViewById(R.id.side_lin);
			final LinearLayout text_lin = (LinearLayout) _view.findViewById(R.id.text_lin);
			final LinearLayout title_time_lin = (LinearLayout) _view.findViewById(R.id.title_time_lin);
			final LinearLayout note_lin = (LinearLayout) _view.findViewById(R.id.note_lin);
			final TextView date = (TextView) _view.findViewById(R.id.date);
			final TextView title = (TextView) _view.findViewById(R.id.title);
			final TextView time = (TextView) _view.findViewById(R.id.time);
			final TextView note = (TextView) _view.findViewById(R.id.note);
			
			date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
			title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
			time.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
			note.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
			if (Selected) {
				imageview1.setImageResource(R.drawable.ic_radio_button_off_black);
				if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Select").toString().equals("True")) {
					imageview1.setVisibility(View.VISIBLE);
					imageview1.setImageResource(R.drawable.ic_radio_button_on_black);
				}
				else {
					imageview1.setImageResource(R.drawable.ic_radio_button_off_black);
				}
			}
			else {
				imageview1.setVisibility(View.GONE);
			}
			if (Settings.getString("Theme", "").equals("Default")) {
				_SX_CornerRadius_4(side_lin, "#3770FD", "#3770FD", 0, 30, 30, 30, 30);
				_SetRadiusToView(main, 30, "#F6F7FB");
				_SetShadowOfView(main, 5);
				_Add("#3770FD", imageview1);
			}
			else {
				if (Settings.getString("Theme", "").equals("Dark")) {
					_SX_CornerRadius_4(side_lin, "#3770FD", "#3770FD", 0, 30, 30, 30, 30);
					_SetRadiusToView(main, 30, "#37474F");
					_SetShadowOfView(main, 5);
					date.setTextColor(0xFFFFFFFF);
					time.setTextColor(0xFFFFFFFF);
					title.setTextColor(0xFFFFFFFF);
					note.setTextColor(0xFFFFFFFF);
					_Add("#FFFFFF", imageview1);
				}
				else {
					if (Settings.getString("Theme", "").equals("Blue Grey")) {
						_SX_CornerRadius_4(side_lin, "#607D8B", "#607D8B", 0, 30, 30, 30, 30);
						_SetRadiusToView(main, 30, "#FFFFFF");
						_SetShadowOfView(main, 5);
						title.setTextColor(0xFF607D8B);
						note.setTextColor(0xFF000000);
						_Add("#607AB7", imageview1);
					}
					else {
						if (Settings.getString("Theme", "").equals("Orange")) {
							_SX_CornerRadius_4(side_lin, "#FF5722", "#FF5722", 0, 30, 30, 30, 30);
							_SetRadiusToView(main, 30, "#FFFFFF");
							_SetShadowOfView(main, 5);
							title.setTextColor(0xFFFF5722);
							note.setTextColor(0xFF000000);
							_Add("#FF5722", imageview1);
						}
						else {
							if (Settings.getString("Theme", "").equals("Indigo")) {
								_SX_CornerRadius_4(side_lin, "#3F51B5", "#3F51B5", 0, 30, 30, 30, 30);
								_SetRadiusToView(main, 30, "#FFFFFF");
								_SetShadowOfView(main, 5);
								title.setTextColor(0xFF3F51B5);
								note.setTextColor(0xFF000000);
								_Add("#3F51B5", imageview1);
							}
							else {
								
							}
						}
					}
				}
			}
			if (Settings.getString("Text Size", "").equals("Small")) {
				
			}
			else {
				if (Settings.getString("Text Size", "").equals("Medium")) {
					_textSize(date, 16);
					_textSize(title, 20);
					_textSize(time, 16);
					_textSize(note, 18);
				}
				else {
					if (Settings.getString("Text Size", "").equals("Large")) {
						_textSize(date, 18);
						_textSize(title, 22);
						_textSize(time, 18);
						_textSize(note, 20);
					}
					else {
						
					}
				}
			}
			if (Notes_List.get((int)(Notes_List.size() - 1) - _position).containsKey("Title")) {
				if (Settings.getString("Text Size", "").equals("Small")) {
					if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString().length() > 28) {
						title.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString().substring((int)(0), (int)(28)).concat("..."));
					}
					else {
						title.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString());
					}
				}
				else {
					if (Settings.getString("Text Size", "").equals("Medium")) {
						if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString().length() > 24) {
							title.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString().substring((int)(0), (int)(24)).concat("..."));
						}
						else {
							title.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString());
						}
					}
					else {
						if (Settings.getString("Text Size", "").equals("Large")) {
							if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString().length() > 22) {
								title.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString().substring((int)(0), (int)(22)).concat("..."));
							}
							else {
								title.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Title").toString());
							}
						}
						else {
							
						}
					}
				}
			}
			if (Notes_List.get((int)(Notes_List.size() - 1) - _position).containsKey("Time")) {
				time.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Time").toString());
			}
			if (Notes_List.get((int)(Notes_List.size() - 1) - _position).containsKey("Date")) {
				date.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Date").toString());
			}
			if (Notes_List.get((int)(Notes_List.size() - 1) - _position).containsKey("Note")) {
				if (Settings.getString("Text Size", "").equals("Small")) {
					if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString().length() > 150) {
						note.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString().substring((int)(0), (int)(150)).concat("..."));
					}
					else {
						note.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString());
					}
				}
				else {
					if (Settings.getString("Text Size", "").equals("Medium")) {
						if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString().length() > 140) {
							note.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString().substring((int)(0), (int)(140)).concat("..."));
						}
						else {
							note.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString());
						}
					}
					else {
						if (Settings.getString("Text Size", "").equals("Large")) {
							if (Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString().length() > 130) {
								note.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString().substring((int)(0), (int)(130)).concat("..."));
							}
							else {
								note.setText(Notes_List.get((int)(Notes_List.size() - 1) - _position).get("Note").toString());
							}
						}
						else {
							
						}
					}
				}
			}
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}