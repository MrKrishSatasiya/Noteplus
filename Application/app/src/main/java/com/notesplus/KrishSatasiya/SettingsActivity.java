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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Switch;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class SettingsActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private double ThemePosition = 0;
	private double SizePosition = 0;
	
	private ArrayList<String> TextSize = new ArrayList<>();
	private ArrayList<String> Themes = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> All_Notes = new ArrayList<>();
	
	private LinearLayout toolbar;
	private ScrollView vscroll;
	private LinearLayout linear1;
	private ImageView back_img;
	private TextView toolbar_txt;
	private LinearLayout main;
	private LinearLayout text_size_lin;
	private LinearLayout line1;
	private LinearLayout theme_lin;
	private LinearLayout line2;
	private LinearLayout linear7;
	private LinearLayout linear5;
	private LinearLayout linear;
	private LinearLayout linear8;
	private LinearLayout detect_link_lin;
	private LinearLayout line3;
	private TextView size_title;
	private TextView size_subtitle;
	private TextView theme_title;
	private TextView theme_subtitle;
	private TextView textview3;
	private TextView textview4;
	private TextView textview6;
	private TextView textview5;
	private TextView link_title;
	private Switch link_subtitle;
	
	private SharedPreferences Settings;
	private AlertDialog.Builder TextSizeDialog;
	private AlertDialog.Builder ThemeDialog;
	private SharedPreferences AllNotes;
	private TimerTask Timer;
	private Intent i = new Intent();
	private AdView mAdView;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		initializeLogic();

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		mAdView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				// Code to be executed when an ad finishes loading.
			}

			@Override
			public void onAdFailedToLoad(LoadAdError adError) {
				// Code to be executed when an ad request fails.
			}

			@Override
			public void onAdOpened() {
				// Code to be executed when an ad opens an overlay that
				// covers the screen.
			}

			@Override
			public void onAdClicked() {
				// Code to be executed when the user clicks on an ad.
			}

			@Override
			public void onAdClosed() {
				// Code to be executed when the user is about to return
				// to the app after tapping on an ad.
			}
		});
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		toolbar = (LinearLayout) findViewById(R.id.toolbar);
		vscroll = (ScrollView) findViewById(R.id.vscroll);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		back_img = (ImageView) findViewById(R.id.back_img);
		toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
		main = (LinearLayout) findViewById(R.id.main);
		text_size_lin = (LinearLayout) findViewById(R.id.text_size_lin);
		line1 = (LinearLayout) findViewById(R.id.line1);
		theme_lin = (LinearLayout) findViewById(R.id.theme_lin);
		line2 = (LinearLayout) findViewById(R.id.line2);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear = (LinearLayout) findViewById(R.id.linear);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		detect_link_lin = (LinearLayout) findViewById(R.id.detect_link_lin);
		line3 = (LinearLayout) findViewById(R.id.line3);
		size_title = (TextView) findViewById(R.id.size_title);
		size_subtitle = (TextView) findViewById(R.id.size_subtitle);
		theme_title = (TextView) findViewById(R.id.theme_title);
		theme_subtitle = (TextView) findViewById(R.id.theme_subtitle);
		textview3 = (TextView) findViewById(R.id.textview3);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview5 = (TextView) findViewById(R.id.textview5);
		link_title = (TextView) findViewById(R.id.link_title);
		link_subtitle = (Switch) findViewById(R.id.link_subtitle);
		Settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		TextSizeDialog = new AlertDialog.Builder(this);
		ThemeDialog = new AlertDialog.Builder(this);
		AllNotes = getSharedPreferences("All Notes", Activity.MODE_PRIVATE);
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		text_size_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://m.facebook.com/krish.satasiya.5811#_=_"));
				startActivity(i);
			}
		});
		
		theme_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://youtube.com/channel/UCUNQ7iaLI1KOX51-5kM1dUg"));
				startActivity(i);
			}
		});
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://krishsatasiya.netlify.app/"));
				startActivity(i);
			}
		});
		
		linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setClass(getApplicationContext(), PrivacyPolicy.class);
				startActivity(i);
			}
		});
		
		detect_link_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (link_subtitle.isChecked()) {
					link_subtitle.setChecked(false);
					Settings.edit().putString("Detect Link", "False").commit();
				}
				else {
					link_subtitle.setChecked(true);
					Settings.edit().putString("Detect Link", "True").commit();
				}
			}
		});
	}
	
	private void initializeLogic() {
		_OnCreate();
		
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _OnCreate () {
		toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		size_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		size_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		theme_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		theme_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		link_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		link_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		_GetSettingsData();
		_ChoiceDialogData();
	}
	
	
	public void _Add (final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _GetSettingsData () {
		size_subtitle.setText("Select The Text Size - ".concat(Settings.getString("Text Size", "")));
		theme_subtitle.setText("Change The App Theme And Enjoy - ".concat(Settings.getString("Theme", "")));
		if (Settings.getString("Detect Link", "").equals("True")) {
			link_subtitle.setChecked(true);
		}
		else {
			link_subtitle.setChecked(false);
		}
		if (Settings.getString("Text Size", "").equals("Small")) {
			
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				_textSize(toolbar_txt, 20);
				_textSize(size_title, 20);
				_textSize(size_subtitle, 16);
				_textSize(theme_title, 20);
				_textSize(theme_subtitle, 16);
				_textSize(link_title, 20);
				_textSize(link_subtitle, 16);
				_textSize(textview3, 20);
				_textSize(textview4, 16);
				_textSize(textview6, 20);
				_textSize(textview5, 16);
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					_textSize(toolbar_txt, 22);
					_textSize(size_title, 22);
					_textSize(size_subtitle, 18);
					_textSize(theme_title, 22);
					_textSize(theme_subtitle, 18);
					_textSize(link_title, 22);
					_textSize(link_subtitle, 18);
					_textSize(textview3, 22);
					_textSize(textview4, 18);
					_textSize(textview6, 22);
					_textSize(textview5, 18);
				}
				else {
					
				}
			}
		}
		if (Settings.getString("Theme", "").equals("Default")) {
			Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			_Add("#3770FD", back_img);
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#263238"));
				_Add("#263238", back_img);
				toolbar.setBackgroundColor(0xFF263238);
				vscroll.setBackgroundColor(0xFF263238);
				toolbar_txt.setTextColor(0xFFFFFFFF);
				size_title.setTextColor(0xFFFFFFFF);
				size_subtitle.setTextColor(0xFFFFFFFF);
				theme_title.setTextColor(0xFFFFFFFF);
				theme_subtitle.setTextColor(0xFFFFFFFF);
				link_title.setTextColor(0xFFFFFFFF);
				link_subtitle.setTextColor(0xFFFFFFFF);
				link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
				line1.setBackgroundColor(0xFFFFFFFF);
				line2.setBackgroundColor(0xFFFFFFFF);
				line3.setBackgroundColor(0xFFFFFFFF);
				linear5.setBackgroundColor(0xFFFFFFFF);
				linear8.setBackgroundColor(0xFFFFFFFF);
				textview3.setTextColor(0xFFFFFFFF);
				textview4.setTextColor(0xFFFFFFFF);
				textview6.setTextColor(0xFFFFFFFF);
				textview5.setTextColor(0xFFFFFFFF);
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					_Add("#607D8B", back_img);
					toolbar.setBackgroundColor(0xFFFFFFFF);
					vscroll.setBackgroundColor(0xFFFFFFFF);
					toolbar_txt.setTextColor(0xFF607D8B);
					size_title.setTextColor(0xFF607D8B);
					size_subtitle.setTextColor(0xFF616161);
					theme_title.setTextColor(0xFF607D8B);
					theme_subtitle.setTextColor(0xFF616161);
					link_title.setTextColor(0xFF607D8B);
					link_subtitle.setTextColor(0xFF616161);
					link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#607D8B"), PorterDuff.Mode.SRC_IN);
					line1.setBackgroundColor(0xFFE0E0E0);
					line2.setBackgroundColor(0xFFE0E0E0);
					line3.setBackgroundColor(0xFFE0E0E0);
					linear5.setBackgroundColor(0xFFE0E0E0);
					linear8.setBackgroundColor(0xFFE0E0E0);
					textview3.setTextColor(0xFF607D8B);
					textview4.setTextColor(0xFF616161);
					textview6.setTextColor(0xFF607D8B);
					textview5.setTextColor(0xFF616161);
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						_Add("#FF5722", back_img);
						toolbar.setBackgroundColor(0xFFFFFFFF);
						vscroll.setBackgroundColor(0xFFFFFFFF);
						toolbar_txt.setTextColor(0xFFFF5722);
						size_title.setTextColor(0xFFFF5722);
						size_subtitle.setTextColor(0xFF616161);
						theme_title.setTextColor(0xFFFF5722);
						theme_subtitle.setTextColor(0xFF616161);
						link_title.setTextColor(0xFFFF5722);
						link_subtitle.setTextColor(0xFF616161);
						link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#FF5722"), PorterDuff.Mode.SRC_IN);
						line1.setBackgroundColor(0xFFE0E0E0);
						line2.setBackgroundColor(0xFFE0E0E0);
						line3.setBackgroundColor(0xFFE0E0E0);
						linear5.setBackgroundColor(0xFFE0E0E0);
						linear8.setBackgroundColor(0xFFE0E0E0);
						textview3.setTextColor(0xFFFF5722);
						textview4.setTextColor(0xFF616161);
						textview6.setTextColor(0xFFFF5722);
						textview5.setTextColor(0xFF616161);
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
							_Add("#3F51B5", back_img);
							toolbar.setBackgroundColor(0xFFFFFFFF);
							vscroll.setBackgroundColor(0xFFFFFFFF);
							toolbar_txt.setTextColor(0xFF3F51B5);
							size_title.setTextColor(0xFF3F51B5);
							size_subtitle.setTextColor(0xFF616161);
							theme_title.setTextColor(0xFF3F51B5);
							theme_subtitle.setTextColor(0xFF616161);
							link_title.setTextColor(0xFF3F51B5);
							link_subtitle.setTextColor(0xFF616161);
							link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#3F51B5"), PorterDuff.Mode.SRC_IN);
							line1.setBackgroundColor(0xFFE0E0E0);
							line2.setBackgroundColor(0xFFE0E0E0);
							line3.setBackgroundColor(0xFFE0E0E0);
							linear5.setBackgroundColor(0xFFE0E0E0);
							linear8.setBackgroundColor(0xFFE0E0E0);
							textview3.setTextColor(0xFF3F51B5);
							textview4.setTextColor(0xFF616161);
							textview6.setTextColor(0xFF3F51B5);
							textview5.setTextColor(0xFF616161);
						}
						else {
							
						}
					}
				}
			}
		}
	}
	
	
	public void _ThemeChoice (final AlertDialog.Builder _Dialog, final ArrayList<String> _ListString) {
		final CharSequence[] _items = _ListString.toArray(new String[_ListString.size()]);
		_Dialog.setSingleChoiceItems(_items, (int)ThemePosition, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ThemePosition = which;
			}});
	}
	
	
	public void _TextSizeChoice (final AlertDialog.Builder _Dialog, final ArrayList<String> _ListString) {
		final CharSequence[] _items = _ListString.toArray(new String[_ListString.size()]);
		_Dialog.setSingleChoiceItems(_items, (int)SizePosition, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SizePosition = which;
			}});
	}
	
	
	public void _ChoiceDialogData () {
		TextSize.add("Small");
		TextSize.add("Medium");
		TextSize.add("Large");
		// Divide
		Themes.add("Default");
		Themes.add("Dark");
		Themes.add("Blue Grey");
		Themes.add("Orange");
		Themes.add("Indigo");
	}
	
	
	public void _textSize (final TextView _TextView1, final double _Size) {
		int j = (int) _Size;
		_TextView1.setTextSize(j);
	}
	
	
	public void _GetTextSize () {
		if (Settings.getString("Text Size", "").equals("Small")) {
			SizePosition = 0;
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				SizePosition = 1;
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					SizePosition = 2;
				}
				else {
					
				}
			}
		}
	}
	
	
	public void _GetThemeData () {
		if (Settings.getString("Theme", "").equals("Default")) {
			ThemePosition = 0;
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				ThemePosition = 1;
				TextSizeDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
				ThemeDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					ThemePosition = 2;
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						ThemePosition = 3;
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							ThemePosition = 4;
						}
						else {
							
						}
					}
				}
			}
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
