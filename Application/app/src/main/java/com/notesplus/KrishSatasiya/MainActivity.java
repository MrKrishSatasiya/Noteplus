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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class MainActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private FloatingActionButton _fab;
	private String App_Theme = "";
	private double Position = 0;
	private HashMap<String, Object> GetNotes = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> Notes_List = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> Important_Notes_List = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout up_lin;
	private LinearLayout btm_lin;
	private LinearLayout toolbar;
	private LinearLayout length_lin;
	private LinearLayout toolbar_txt_lin;
	private ImageView ic_settings;
	private TextView welcome_title;
	private TextView notes_subtitle;
	private LinearLayout notes_im_lin;
	private LinearLayout notes_lin;
	private LinearLayout important_lin;
	private TextView notes_length_title;
	private TextView notes_length;
	private TextView important_len_title;
	private TextView important_length;
	private TextView recent_title;
	private LinearLayout notes_list_lin;
	private LinearLayout notes_list_lin_main;
	private LinearLayout empty_lin;
	private LinearLayout note_main;
	private LinearLayout side_lin;
	private LinearLayout text_lin;
	private LinearLayout title_time_lin;
	private LinearLayout note_lin;
	private TextView date;
	private TextView title;
	private TextView time;
	private TextView note;
	private ImageView empty_img;
	private TextView empty_txt;
	
	private SharedPreferences AllNotes;
	private TimerTask Anim;
	private Intent To_Add_New = new Intent();
	private Intent To_Notes = new Intent();
	private Intent To_Settings = new Intent();
	private Intent To_Note_View = new Intent();
	private SharedPreferences Settings_Setup;
	private SharedPreferences Settings;
	private AlertDialog.Builder d;
	private Intent inm = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		main = (LinearLayout) findViewById(R.id.main);
		up_lin = (LinearLayout) findViewById(R.id.up_lin);
		btm_lin = (LinearLayout) findViewById(R.id.btm_lin);
		toolbar = (LinearLayout) findViewById(R.id.toolbar);
		length_lin = (LinearLayout) findViewById(R.id.length_lin);
		toolbar_txt_lin = (LinearLayout) findViewById(R.id.toolbar_txt_lin);
		ic_settings = (ImageView) findViewById(R.id.ic_settings);
		welcome_title = (TextView) findViewById(R.id.welcome_title);
		notes_subtitle = (TextView) findViewById(R.id.notes_subtitle);
		notes_im_lin = (LinearLayout) findViewById(R.id.notes_im_lin);
		notes_lin = (LinearLayout) findViewById(R.id.notes_lin);
		important_lin = (LinearLayout) findViewById(R.id.important_lin);
		notes_length_title = (TextView) findViewById(R.id.notes_length_title);
		notes_length = (TextView) findViewById(R.id.notes_length);
		important_len_title = (TextView) findViewById(R.id.important_len_title);
		important_length = (TextView) findViewById(R.id.important_length);
		recent_title = (TextView) findViewById(R.id.recent_title);
		notes_list_lin = (LinearLayout) findViewById(R.id.notes_list_lin);
		notes_list_lin_main = (LinearLayout) findViewById(R.id.notes_list_lin_main);
		empty_lin = (LinearLayout) findViewById(R.id.empty_lin);
		note_main = (LinearLayout) findViewById(R.id.note_main);
		side_lin = (LinearLayout) findViewById(R.id.side_lin);
		text_lin = (LinearLayout) findViewById(R.id.text_lin);
		title_time_lin = (LinearLayout) findViewById(R.id.title_time_lin);
		note_lin = (LinearLayout) findViewById(R.id.note_lin);
		date = (TextView) findViewById(R.id.date);
		title = (TextView) findViewById(R.id.title);
		time = (TextView) findViewById(R.id.time);
		note = (TextView) findViewById(R.id.note);
		empty_img = (ImageView) findViewById(R.id.empty_img);
		empty_txt = (TextView) findViewById(R.id.empty_txt);
		AllNotes = getSharedPreferences("All Notes", Activity.MODE_PRIVATE);
		Settings_Setup = getSharedPreferences("Settings Set Up", Activity.MODE_PRIVATE);
		Settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
		
		ic_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Animator(ic_settings, "scaleX", 1.1d, 200);
				_Animator(ic_settings, "scaleY", 1.1d, 200);
				Anim = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_Animator(ic_settings, "scaleX", 1, 200);
								_Animator(ic_settings, "scaleY", 1, 200);
								To_Settings.setAction(Intent.ACTION_VIEW);
								To_Settings.setClass(getApplicationContext(), SettingsActivity.class);
								startActivity(To_Settings);
							}
						});
					}
				};
				_timer.schedule(Anim, (int)(200));
			}
		});
		
		notes_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Animator(notes_lin, "scaleX", 1.05d, 200);
				_Animator(notes_lin, "scaleY", 1.05d, 200);
				Anim = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_Animator(notes_lin, "scaleX", 1, 200);
								_Animator(notes_lin, "scaleY", 1, 200);
								To_Notes.setAction(Intent.ACTION_VIEW);
								To_Notes.setClass(getApplicationContext(), NotesListActivity.class);
								To_Notes.putExtra("Data", new Gson().toJson(Notes_List));
								To_Notes.putExtra("Type", "All");
								startActivity(To_Notes);
							}
						});
					}
				};
				_timer.schedule(Anim, (int)(200));
			}
		});
		
		important_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Animator(important_lin, "scaleX", 1.05d, 200);
				_Animator(important_lin, "scaleY", 1.05d, 200);
				Anim = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_Animator(important_lin, "scaleX", 1, 200);
								_Animator(important_lin, "scaleY", 1, 200);
								To_Notes.setAction(Intent.ACTION_VIEW);
								To_Notes.setClass(getApplicationContext(), NotesListActivity.class);
								To_Notes.putExtra("Data", new Gson().toJson(Important_Notes_List));
								To_Notes.putExtra("Type", "Important");
								startActivity(To_Notes);
							}
						});
					}
				};
				_timer.schedule(Anim, (int)(200));
			}
		});
		
		note_main.setOnLongClickListener(new View.OnLongClickListener() {
			 @Override
				public boolean onLongClick(View _view) {
				Intent i = new Intent(android.content.Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(android.content.Intent.EXTRA_TEXT, title.getText().toString()); startActivity(Intent.createChooser(i,note.getText().toString()));
				return true;
				}
			 });
		
		note_main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				To_Note_View.setAction(Intent.ACTION_VIEW);
				To_Note_View.setClass(getApplicationContext(), NoteViewActivity.class);
				To_Note_View.putExtra("Position", String.valueOf((long)(Notes_List.size() - 1)));
				startActivity(To_Note_View);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				To_Add_New.setAction(Intent.ACTION_VIEW);
				To_Add_New.setClass(getApplicationContext(), AddNoteActivity.class);
				startActivity(To_Add_New);
			}
		});
	}
	
	private void initializeLogic() {
		App_Theme = "#3770FD";
		_DynamicShortcutApp();
		_UIDESIGN();
		_ripple(ic_settings);
		_ONCREATE_LIST();
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
	public void onStart() {
		super.onStart();
		_ONCREATE_LIST();
		com.google.android.material.snackbar.Snackbar.make(main, "Welcome to Note Plus Application", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("", new View.OnClickListener(){
			@Override
			public void onClick(View _view) {
				 
			}
		}).show();
	}
	
	@Override
	public void onBackPressed() {
		d.setMessage("Are You Want To Exit !");
		d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finish();
			}
		});
		d.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		d.setNeutralButton("Rate Us ⭐", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				inm.setAction(Intent.ACTION_VIEW);
				inm.setData(Uri.parse("https://play.google.com/store/apps/dev?id=8452114975424851755"));
				startActivity(inm);
			}
		});
		d.create().show();
	}
	public void _UIDESIGN () {
		_Add(App_Theme, empty_img);
		_SX_CornerRadius_4(side_lin, App_Theme, App_Theme, 0, 30, 30, 30, 30);
		_SetRadiusToView(note_main, 30, "#F6F7FB");
		_SetShadowOfView(note_main, 5);
		_SetRadiusToView(important_lin, 30, "#F6F7FB");
		_SetShadowOfView(notes_lin, 10);
		_SetShadowOfView(important_lin, 10);
		int[] colors = {Color.parseColor(App_Theme), Color.parseColor("#3E82FF")};
		
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TR_BL, colors);
		gd.setCornerRadius(30f);
		notes_lin.setBackgroundDrawable(gd);
	}
	
	
	public void _SetRadiusToView (final View _view, final double _radius, final String _Colour) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(); gd.setColor(Color.parseColor(_Colour)); gd.setCornerRadius((int)_radius); _view.setBackground(gd);
	}
	
	
	public void _SetShadowOfView (final View _view, final double _value) {
		_view.setElevation((float)_value);
	}
	
	
	public void _Add (final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _SX_CornerRadius_4 (final View _view, final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		
		gd.setColor(Color.parseColor(_color1));
		
		gd.setStroke((int)_str, Color.parseColor(_color2));
		
		gd.setCornerRadii(new float[]{(int)_n1,(int)_n1,(int)_n2,(int)_n2,(int)_n3,(int)_n3,(int)_n4,(int)_n4});
		
		_view.setBackground(gd);
		
		_view.setElevation(4);
	}
	
	
	public void _ripple (final View _view) {
		
		int[] attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
		android.content.res.TypedArray typedArray = this.obtainStyledAttributes(attrs);
		int backgroundResource = typedArray.getResourceId(0, 0); _view.setBackgroundResource(backgroundResource);
		_view.setClickable(true);
	}
	
	
	public void _Animator (final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.start();
	}
	
	
	public void _ONCREATE_LIST () {
		_Preferances();
		_GetSettingsData();
		_Typeface();
		if (!AllNotes.getString("Notes", "").equals("")) {
			Notes_List = new Gson().fromJson(AllNotes.getString("Notes", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			_GetImportantNotes();
		}
		if (Notes_List.size() == 0) {
			notes_length.setText("0");
			important_length.setText("0");
			notes_list_lin_main.setVisibility(View.GONE);
			recent_title.setVisibility(View.GONE);
			empty_lin.setVisibility(View.VISIBLE);
		}
		else {
			_GetRecentDataFrom(Notes_List.size() - 1);
			notes_length.setText(String.valueOf((long)(Notes_List.size())));
			important_length.setText(String.valueOf((long)(Important_Notes_List.size())));
			notes_list_lin_main.setVisibility(View.VISIBLE);
			recent_title.setVisibility(View.VISIBLE);
			empty_lin.setVisibility(View.GONE);
		}
	}
	
	
	public void _Preferances () {
		if (Settings_Setup.getString("Settings Set Up", "").equals("")) {
			Settings_Setup.edit().putString("Settings Set Up", "True").commit();
			Settings.edit().putString("Text Size", "Small").commit();
			Settings.edit().putString("Theme", "Default").commit();
			Settings.edit().putString("Detect Link", "True").commit();
		}
		else {
			
		}
	}
	
	
	public void _GetSettingsData () {
		if (Settings.getString("Text Size", "").equals("Small")) {
			
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				_textSize(welcome_title, 28);
				_textSize(notes_subtitle, 18);
				_textSize(notes_length_title, 22);
				_textSize(notes_length, 18);
				_textSize(important_len_title, 22);
				_textSize(important_length, 18);
				_textSize(recent_title, 20);
				_textSize(date, 16);
				_textSize(title, 20);
				_textSize(time, 16);
				_textSize(note, 18);
				_textSize(empty_txt, 20);
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					_textSize(welcome_title, 32);
					_textSize(notes_subtitle, 20);
					_textSize(notes_length_title, 24);
					_textSize(notes_length, 20);
					_textSize(important_len_title, 24);
					_textSize(important_length, 20);
					_textSize(recent_title, 22);
					_textSize(date, 18);
					_textSize(title, 22);
					_textSize(time, 18);
					_textSize(note, 20);
					_textSize(empty_txt, 22);
				}
			}
		}
		if (Settings.getString("Theme", "").equals("Default")) {
			Window w = MainActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			_UIDESIGN();
			_Add("#000000", ic_settings);
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				Window w = MainActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#263238"));
				main.setBackgroundColor(0xFF263238);
				notes_subtitle.setTextColor(0xFFFFFFFF);
				welcome_title.setTextColor(0xFFFFFFFF);
				recent_title.setTextColor(0xFFFFFFFF);
				_Add("#FFFFFF", ic_settings);
				_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#263238")));
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					Window w = MainActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					main.setBackgroundColor(0xFFFFFFFF);
					welcome_title.setTextColor(0xFF607D8B);
					_Add("#607D8B", ic_settings);
					_Add("#607D8B", empty_img);
					empty_txt.setTextColor(0xFF607D8B);
					recent_title.setTextColor(0xFF607D8B);
					important_len_title.setTextColor(0xFF607D8B);
					important_length.setTextColor(0xFF607D8B);
					title.setTextColor(0xFF607D8B);
					int[] colors = {Color.parseColor("#607D8B"), Color.parseColor("#546E7A")};
					
					android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TR_BL, colors);
					gd.setCornerRadius(30f);
					notes_lin.setBackgroundDrawable(gd);
					_SetRadiusToView(note_main, 30, "#FFFFFF");
					_SetShadowOfView(note_main, 5);
					_SX_CornerRadius_4(side_lin, "#607D8B", "#607D8B", 0, 30, 30, 30, 30);
					_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#607D8B")));
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						Window w = MainActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						main.setBackgroundColor(0xFFFFFFFF);
						welcome_title.setTextColor(0xFFFF5722);
						_Add("#FF5722", ic_settings);
						_Add("#FF5722", empty_img);
						empty_txt.setTextColor(0xFFFF5722);
						recent_title.setTextColor(0xFFFF5722);
						important_len_title.setTextColor(0xFFFF5722);
						important_length.setTextColor(0xFFFF5722);
						title.setTextColor(0xFFFF5722);
						int[] colors = {Color.parseColor("#FF5722"), Color.parseColor("#FF7043")};
						
						android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TR_BL, colors);
						gd.setCornerRadius(30f);
						notes_lin.setBackgroundDrawable(gd);
						_SetRadiusToView(note_main, 30, "#FFFFFF");
						_SetShadowOfView(note_main, 5);
						_SX_CornerRadius_4(side_lin, "#FF5722", "#FF5722", 0, 30, 30, 30, 30);
						_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FF5722")));
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							Window w = MainActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
							main.setBackgroundColor(0xFFFFFFFF);
							welcome_title.setTextColor(0xFF3F51B5);
							_Add("#3F51B5", ic_settings);
							_Add("#3F51B5", empty_img);
							empty_txt.setTextColor(0xFF3F51B5);
							recent_title.setTextColor(0xFF3F51B5);
							important_len_title.setTextColor(0xFF3F51B5);
							important_length.setTextColor(0xFF3F51B5);
							title.setTextColor(0xFF3F51B5);
							int[] colors = {Color.parseColor("#3F51B5"), Color.parseColor("#3949AB")};
							
							android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TR_BL, colors);
							gd.setCornerRadius(30f);
							notes_lin.setBackgroundDrawable(gd);
							_SetRadiusToView(note_main, 30, "#FFFFFF");
							_SetShadowOfView(note_main, 5);
							_SX_CornerRadius_4(side_lin, "#3F51B5", "#3F51B5", 0, 30, 30, 30, 30);
							_fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#3F51B5")));
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
	
	
	public void _Typeface () {
		welcome_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		notes_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		notes_length_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		notes_length.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		important_len_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		important_length.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		recent_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		time.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		note.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		empty_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
	}
	
	
	public void _GetRecentDataFrom (final double _Position) {
		if (Notes_List.get((int)_Position).containsKey("Title")) {
			if (Settings.getString("Text Size", "").equals("Small")) {
				if (Notes_List.get((int)_Position).get("Title").toString().length() > 28) {
					title.setText(Notes_List.get((int)_Position).get("Title").toString().substring((int)(0), (int)(28)).concat("..."));
				}
				else {
					title.setText(Notes_List.get((int)_Position).get("Title").toString());
				}
			}
			else {
				if (Settings.getString("Text Size", "").equals("Medium")) {
					if (Notes_List.get((int)_Position).get("Title").toString().length() > 24) {
						title.setText(Notes_List.get((int)_Position).get("Title").toString().substring((int)(0), (int)(24)).concat("..."));
					}
					else {
						title.setText(Notes_List.get((int)_Position).get("Title").toString());
					}
				}
				else {
					if (Settings.getString("Text Size", "").equals("Large")) {
						if (Notes_List.get((int)_Position).get("Title").toString().length() > 22) {
							title.setText(Notes_List.get((int)_Position).get("Title").toString().substring((int)(0), (int)(22)).concat("..."));
						}
						else {
							title.setText(Notes_List.get((int)_Position).get("Title").toString());
						}
					}
					else {
						
					}
				}
			}
		}
		if (Notes_List.get((int)_Position).containsKey("Time")) {
			time.setText(Notes_List.get((int)_Position).get("Time").toString());
		}
		if (Notes_List.get((int)_Position).containsKey("Date")) {
			date.setText(Notes_List.get((int)_Position).get("Date").toString());
		}
		if (Notes_List.get((int)_Position).containsKey("Note")) {
			if (Settings.getString("Text Size", "").equals("Small")) {
				if (Notes_List.get((int)_Position).get("Note").toString().length() > 150) {
					note.setText(Notes_List.get((int)_Position).get("Note").toString().substring((int)(0), (int)(150)).concat("..."));
				}
				else {
					note.setText(Notes_List.get((int)_Position).get("Note").toString());
				}
			}
			else {
				if (Settings.getString("Text Size", "").equals("Medium")) {
					if (Notes_List.get((int)_Position).get("Note").toString().length() > 140) {
						note.setText(Notes_List.get((int)_Position).get("Note").toString().substring((int)(0), (int)(140)).concat("..."));
					}
					else {
						note.setText(Notes_List.get((int)_Position).get("Note").toString());
					}
				}
				else {
					if (Settings.getString("Text Size", "").equals("Large")) {
						if (Notes_List.get((int)_Position).get("Note").toString().length() > 130) {
							note.setText(Notes_List.get((int)_Position).get("Note").toString().substring((int)(0), (int)(130)).concat("..."));
						}
						else {
							note.setText(Notes_List.get((int)_Position).get("Note").toString());
						}
					}
					else {
						
					}
				}
			}
		}
	}
	
	
	public void _GetImportantNotes () {
		Important_Notes_List.clear();
		Position = 0;
		for(int _repeat10 = 0; _repeat10 < (int)(Notes_List.size()); _repeat10++) {
			if (Notes_List.get((int)Position).get("Important").toString().equals("True")) {
				GetNotes = Notes_List.get((int)Position);
				Important_Notes_List.add(GetNotes);
			}
			Position++;
		}
	}
	
	
	public void _DynamicShortcutApp () {
		android.content.pm.ShortcutManager shortcutManager = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
			shortcutManager = getSystemService(android.content.pm.ShortcutManager.class);
		}
		if (shortcutManager != null) {
			android.content.pm.ShortcutInfo shortcut_1 = new android.content.pm.ShortcutInfo.Builder(MainActivity.this, "Activity1")
								.setShortLabel("Add Note")
								.setLongLabel("Add New Note")
								.setRank(0)
								.setIntent(new android.content.Intent(android.content.Intent.ACTION_VIEW, null, MainActivity.this, AddNoteActivity.class))
								.setIcon(android.graphics.drawable.Icon.createWithResource(MainActivity.this, R.drawable.add_note))
								.build();
			android.content.pm.ShortcutInfo shortcut_2= new android.content.pm.ShortcutInfo.Builder(MainActivity.this, "Activity2")
								.setShortLabel("Settings")
								.setLongLabel("Go To Settings")
								.setRank(1)
								.setIntent(new android.content.Intent(android.content.Intent.ACTION_VIEW, null, MainActivity.this, SettingsActivity.class))
								.setIcon(android.graphics.drawable.Icon.createWithResource(MainActivity.this, R.drawable.settings))
								.build();
			shortcutManager.setDynamicShortcuts(java.util.Arrays.asList(shortcut_1,shortcut_2));
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
