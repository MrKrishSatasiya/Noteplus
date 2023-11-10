package com.notesplus.KrishSatasiya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
import android.graphics.Typeface;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AddNoteActivity extends  AppCompatActivity  { 
	
	
	private String Date = "";
	private String Time = "";
	private HashMap<String, Object> AddNote = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> Notes_List = new ArrayList<>();
	
	private LinearLayout toolbar;
	private LinearLayout main;
	private ImageView back_img;
	private EditText title_edittext;
	private EditText note_edittext;
	private LinearLayout line;
	private LinearLayout check_lin;
	private CheckBox importantcheckbox;
	private TextView characters;
	
	private Calendar Cal = Calendar.getInstance();
	private SharedPreferences AllNotes;
	private SharedPreferences Settings;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add_note);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		toolbar = (LinearLayout) findViewById(R.id.toolbar);
		main = (LinearLayout) findViewById(R.id.main);
		back_img = (ImageView) findViewById(R.id.back_img);
		title_edittext = (EditText) findViewById(R.id.title_edittext);
		note_edittext = (EditText) findViewById(R.id.note_edittext);
		line = (LinearLayout) findViewById(R.id.line);
		check_lin = (LinearLayout) findViewById(R.id.check_lin);
		importantcheckbox = (CheckBox) findViewById(R.id.importantcheckbox);
		characters = (TextView) findViewById(R.id.characters);
		AllNotes = getSharedPreferences("All Notes", Activity.MODE_PRIVATE);
		Settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (title_edittext.getText().toString().equals("") && note_edittext.getText().toString().equals("")) {
					android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
					finish();
				}
				else {
					if (title_edittext.getText().toString().equals("") && !note_edittext.getText().toString().equals("")) {
						
					}
					else {
						if (note_edittext.getText().toString().equals("") && !title_edittext.getText().toString().equals("")) {
							
						}
						else {
							AddNote = new HashMap<>();
							AddNote.put("Note", note_edittext.getText().toString());
							AddNote.put("Title", title_edittext.getText().toString());
							if (importantcheckbox.isChecked()) {
								AddNote.put("Important", "True");
							}
							else {
								AddNote.put("Important", "False");
							}
							AddNote.put("Date", Date);
							AddNote.put("Time", Time);
							AddNote.put("Select", "False");
							Notes_List.add(AddNote);
							AllNotes.edit().putString("Notes", new Gson().toJson(Notes_List)).commit();
							finish();
						}
					}
				}
			}
		});
		
		note_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				characters.setText(String.valueOf((long)(_charSeq.length())).concat(" Characters"));
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		_ONCREATE();
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
		back_img.performClick();
	}
	public void _Typeface () {
		importantcheckbox.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		title_edittext.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		note_edittext.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		characters.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
	}
	
	
	public void _ONCREATE () {
		Cal = Calendar.getInstance();
		_Typeface();
		if (!AllNotes.getString("Notes", "").equals("")) {
			Notes_List = new Gson().fromJson(AllNotes.getString("Notes", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
		Date = new SimpleDateFormat("dd MMM").format(Cal.getTime());
		Time = new SimpleDateFormat("hh:mm a").format(Cal.getTime());
		importantcheckbox.setChecked(false);
		_Capitalize(title_edittext);
		_Capitalize(note_edittext);
		_GetSettingsData();
	}
	
	
	public void _Capitalize (final TextView _Edittext) {
		_Edittext.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
	}
	
	
	public void _GetSettingsData () {
		if (Settings.getString("Text Size", "").equals("Small")) {
			
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				_textSize(title_edittext, 18);
				_textSize(note_edittext, 18);
				_textSize(importantcheckbox, 18);
				_textSize(characters, 18);
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					_textSize(title_edittext, 20);
					_textSize(note_edittext, 20);
					_textSize(importantcheckbox, 20);
					_textSize(characters, 20);
				}
				else {
					
				}
			}
		}
		if (Settings.getString("Theme", "").equals("Default")) {
			Window w = AddNoteActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			_Add("#000000", back_img);
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				Window w = AddNoteActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#000000"));
				toolbar.setBackgroundColor(0xFF263238);
				main.setBackgroundColor(0xFF263238);
				title_edittext.setTextColor(0xFFFFFFFF);
				title_edittext.setHintTextColor(0xFFFFFFFF);
				note_edittext.setTextColor(0xFFFFFFFF);
				note_edittext.setHintTextColor(0xFFFFFFFF);
				importantcheckbox.setTextColor(0xFFFFFFFF);
				check_lin.setBackgroundColor(0xFF263238);
				characters.setTextColor(0xFFFFFFFF);
				importantcheckbox.getButtonDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
				_Add("#FFFFFF", back_img);
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					Window w = AddNoteActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					toolbar.setBackgroundColor(0xFFFFFFFF);
					main.setBackgroundColor(0xFFFFFFFF);
					title_edittext.setTextColor(0xFF607D8B);
					title_edittext.setHintTextColor(0xFF607D8B);
					note_edittext.setTextColor(0xFF000000);
					note_edittext.setHintTextColor(0xFF000000);
					importantcheckbox.setTextColor(0xFF607D8B);
					importantcheckbox.getButtonDrawable().setColorFilter(Color.parseColor("#607D8B"), PorterDuff.Mode.SRC_IN);
					_Add("#000000", back_img);
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						Window w = AddNoteActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						toolbar.setBackgroundColor(0xFFFFFFFF);
						main.setBackgroundColor(0xFFFFFFFF);
						title_edittext.setTextColor(0xFFFF5722);
						title_edittext.setHintTextColor(0xFFFF5722);
						note_edittext.setTextColor(0xFF000000);
						note_edittext.setHintTextColor(0xFF000000);
						importantcheckbox.setTextColor(0xFFFF5722);
						importantcheckbox.getButtonDrawable().setColorFilter(Color.parseColor("#FF5722"), PorterDuff.Mode.SRC_IN);
						_Add("#000000", back_img);
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							Window w = AddNoteActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
							toolbar.setBackgroundColor(0xFFFFFFFF);
							main.setBackgroundColor(0xFFFFFFFF);
							title_edittext.setTextColor(0xFF3F51B5);
							title_edittext.setHintTextColor(0xFF3F51B5);
							note_edittext.setTextColor(0xFF000000);
							note_edittext.setHintTextColor(0xFF000000);
							importantcheckbox.setTextColor(0xFF3F51B5);
							importantcheckbox.getButtonDrawable().setColorFilter(Color.parseColor("#3F51B5"), PorterDuff.Mode.SRC_IN);
							_Add("#000000", back_img);
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
	
	
	public void _Add (final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
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