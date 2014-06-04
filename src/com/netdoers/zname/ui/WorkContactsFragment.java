/* HISTORY
 * CATEGORY			 :- WORK GROUP FRAGMENT
 * DEVELOPER		 :- VIKALP PATEL
 * AIM      		 :- WORK CONTACT FRAGMENTS ATTACHED TO MOTHERACTIVITY USING VIEWPAGER + TABS
 * NOTE: 
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * ZM001      VIKALP PATEL     16/05/2014                       CREATED
 * ZM002      VIKALP PATEL     27/05/2014                       CLONED FROM FRIENDS FRAGMENT
 * ZM003      VIKALP PATEL     30/05/2014                       SUPPRESSED FRAGMENT WISE ACTION BAR MENU
 * ZM004      VIKALP PATEL     03/06/2014                       MOVE SEARCH INTO SEARCH ACTIVITY
 * --------------------------------------------------------------------------------------------------------------------
 */

package com.netdoers.zname.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.netdoers.zname.AppConstants;
import com.netdoers.zname.R;
import com.netdoers.zname.beans.Contact;
import com.netdoers.zname.beans.ContactPicker;
import com.netdoers.zname.contactpicker.ContactPickerManager;
import com.netdoers.zname.sqlite.DBConstant;

/**
 * @author Vikalp Patel(vikalppatelce@yahoo.com)
 *
 */
public class WorkContactsFragment extends SherlockFragment {

	//DECLARE VARIABLES
	GridView contactsGridView;
	Button addContact;
//	LinearLayout searchContactLayout; SU ZM004
//	ImageView searchClose;	
//	EditText searchField; EU ZM004
	

	//TYPEFACE
	static Typeface styleFont;
	
    //ADAPTER
	private ContactAdapter contactAdapter = null;
	
	//REFERENCES VARIABLE - HELPER
	private ArrayList<Contact> contacts = null;
	private LinkedHashMap<String, Contact> allContacts = new LinkedHashMap<String, Contact>();
	
	
	//INDEXING FOR THE LIST
	HashMap<String, Integer> alphaIndexer;
	String[] sections;

	//CONSTANTS
	public static final String TAG = "FriendsContactsFragment";
	public static final int ADD_CONTACT = 10001;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setHasOptionsMenu(true); COMMENTED ZM003
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab2.xml
		View view = inflater.inflate(R.layout.fragment_friends, container, false);
		contactsGridView = (GridView)view.findViewById(R.id.gridview_friends);
		addContact = (Button) view.findViewById(R.id.friends_btn_add);
//		searchContactLayout = (LinearLayout)view.findViewById(R.id.friends_search_txt_layout); SU ZM004
//		searchClose = (ImageView)view.findViewById(R.id.friends_clear_srch_button);
//		searchField = (EditText) view.findViewById(R.id.friends_search_txt); EU ZM004
		return view;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		styleFont = Typeface.createFromAsset(getActivity().getAssets(), AppConstants.fontStyle);
		
	// View Listeners
//		SU ZM004
//	searchClose.setOnClickListener(new OnClickListener() {
//	@Override
//		public void onClick(View v) {
//		// TODO Auto-generated method stub
//		onCloseSearchLayout(v);	
//		}
//	});
//	searchField.addTextChangedListener(new TextWatcher() {
//		@Override
//		public void afterTextChanged(Editable s) {
//		}
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//		}
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//			// call the filter with the current text on the editbox
//			try {
//				contactAdapter.getFilter().filter(s.toString());
//			} catch (Exception e) {
//				Log.e(TAG, e.toString());
//			}
//		}
//	});
//		EU ZM004
	
	contactsGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			// vibration for 100 milliseconds
			((Vibrator)getActivity().getApplication().getApplicationContext().getSystemService(getActivity().VIBRATOR_SERVICE)).vibrate(50);
			
			String viewTagNumber = view.getTag(R.id.TAG_CONTACT_NUMBER).toString();
			String viewTagDp = view.getTag(R.id.TAG_CONTACT_DP).toString();
			String viewTagName =  view.getTag(R.id.TAG_CONTACT_NAME).toString();
			
			showInputDialog(viewTagName,viewTagNumber,viewTagDp);
			return false;
		}
	});
	addContact.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			openAddContactsLayout();
		}
	});
	contacts = new ArrayList<Contact>();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
			refreshContactsData();
	}
//	 SC ZM003
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//	    super.onCreateOptionsMenu(menu, inflater);
//	    menu.clear();
//	    inflater.inflate(R.menu.friends_contacts_menu, menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.friends_action_search:
//			openSearchLayout();
//			return true;
//		case R.id.action_add:
//			openAddContactsLayout();
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
//	EC ZM003
	
//	View Listeners
//	SU ZM004
//	public void onCloseSearchLayout(View v)
//	{
//		searchContactLayout.setVisibility(View.GONE);
//		searchField.setText("");
//	}
//
//	public void openSearchLayout()
//	{
//		searchContactLayout.setVisibility(View.VISIBLE);
//	}
//	EU ZM004
	public void openAddContactsLayout()
	{
		Intent addContacts = new Intent(getActivity(), ContactPickerManager.class);
		startActivityForResult(addContacts, ADD_CONTACT);
	}
//	ALERT DIALOG
	public void showInputDialog(String name,String number,String photoUri)
	{
		final Dialog dialog = new Dialog(getActivity());
		try {
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		} catch (Exception e) {
			Log.e("inputDialog", e.toString());
		}
		dialog.setContentView(R.layout.grid_view_alert_dialog);
		
		TextView gridAlertName = (TextView)dialog.findViewById(R.id.grid_alert_display_name);
		TextView gridAlertCall = (TextView)dialog.findViewById(R.id.grid_alert_call);
		TextView gridAlertMessage = (TextView)dialog.findViewById(R.id.grid_alert_message);
		ImageView gridAlertImage = (ImageView)dialog.findViewById(R.id.grid_alert_contact_image);
		Button gridAlertCancel = (Button)dialog.findViewById(R.id.grid_alert_cancel);
		
		gridAlertName.setText(name);
		
		gridAlertMessage.setTypeface(styleFont);
		gridAlertCall.setTypeface(styleFont);
		gridAlertName.setTypeface(styleFont);
		gridAlertCancel.setTypeface(styleFont);
		
		if (!TextUtils.isEmpty(photoUri)) {
			gridAlertImage.setImageURI(Uri.parse(photoUri));
		}
		
		final String contactNumber = number; 
		
		if (gridAlertImage.getDrawable() == null)
			gridAlertImage.setImageResource(R.drawable.def_contact);
		
		gridAlertCall.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri.parse("tel:"+Uri.encode(contactNumber)));
				startActivity(callIntent);				
			}
		});
		
		gridAlertMessage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+Uri.encode(contactNumber)));
	            startActivity(smsIntent);
			}
		});
		
		gridAlertCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}
//	ADD CONTACTS TO CONTACTS ADAPTER
	
	public void refreshContactsData()
	{
		Cursor cr = getActivity().getContentResolver().query(DBConstant.Work_Contacts_Columns.CONTENT_URI, null, null, null, DBConstant.Work_Contacts_Columns.COLUMN_DISPLAY_NAME + " ASC");
		Cursor crAll = getActivity().getContentResolver().query(DBConstant.All_Contacts_Columns.CONTENT_URI, null, null, null, null);
		Cursor cursor = null;
		if(cr.getCount() > 0)
		{
			contacts.clear();
			
			int intColumnId = cr.getColumnIndex(DBConstant.Work_Contacts_Columns.COLUMN_CONTACT_ID);
			int intColumnContactNumber = crAll.getColumnIndex(DBConstant.All_Contacts_Columns.COLUMN_CONTACT_NUMBER);
			int intColumnZname = crAll.getColumnIndex(DBConstant.All_Contacts_Columns.COLUMN_DISPLAY_NAME);
			int intColumnZnameDp = crAll.getColumnIndex(DBConstant.All_Contacts_Columns.COLUMN_CALL_STATUS);
			if(crAll!=null){
				crAll.close();	
			}
			
			Contact c;
			cr.moveToFirst();
			while(cr.moveToNext())
			{
				c = new Contact();
				c.setContactId(cr.getString(intColumnId));
				
				cursor = getActivity().getContentResolver().query(DBConstant.All_Contacts_Columns.CONTENT_URI, null, DBConstant.All_Contacts_Columns.COLUMN_CONTACT_ID+"=?", new String[]{cr.getString(intColumnId)}, null);
				if(cursor.getCount() > 0)
				{
					cursor.moveToFirst();
					
					c.setContactNumber(cursor.getString(intColumnContactNumber));
					c.setContactName(cursor.getString(intColumnZname));
					c.setContactPhotoUri(Uri.parse(cursor.getString(intColumnZnameDp)));
					contacts.add(c);
				}
				if(cursor!=null){
					cursor.close();
				}
			}
		}
		if(cr!=null){
			cr.close();
		}
		if(cursor!=null){
			cursor.close();
		}
		
		if(contacts.size() > 0){
			contactAdapter = new ContactAdapter(getActivity(), R.id.gridview_friends, contacts);
			contactsGridView.setAdapter(contactAdapter);	
		}
	}
	
// ON ACTIVITY RESULT
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ADD_CONTACT) {
			if (resultCode == Activity.RESULT_OK) {
				if (data.hasExtra(ContactPicker.CONTACTS_DATA)) {
					ArrayList<ContactPicker> contacts = data.getParcelableArrayListExtra(ContactPicker.CONTACTS_DATA);
					if(contacts != null) {
						Iterator<ContactPicker> iterContacts = contacts.iterator();
						while (iterContacts.hasNext()) {
								final ContactPicker contact = iterContacts.next();
								ContentValues values = new ContentValues();
								try {
								values.put(DBConstant.Work_Contacts_Columns.COLUMN_CONTACT_ID, contact.getContactId().toString());
								values.put(DBConstant.Work_Contacts_Columns.COLUMN_DISPLAY_NAME, contact.getContactName().toString());
								getActivity().getContentResolver().insert(DBConstant.Work_Contacts_Columns.CONTENT_URI, values);
								refreshContactsData();
							} catch (Exception e) {
								Log.e(TAG, e.toString());
							}
						}
					}
				}
			}
		}
	}
	
//	VIEW ADAPTER
/*
 * All Contacts Adapter
 */
	// Contact adapter
	public class ContactAdapter extends ArrayAdapter<Contact> implements SectionIndexer {

		private ArrayList<Contact> contactList;
		private ArrayList<Contact> originalList;
		private ContactFilter filter;

		public ContactAdapter(Context context, int textViewResourceId, ArrayList<Contact> items) {
			super(context, textViewResourceId, items);

			this.contactList = new ArrayList<Contact>();
			this.originalList = new ArrayList<Contact>();

			this.contactList.addAll(items);
			this.originalList.addAll(items);

			// indexing
			alphaIndexer = new HashMap<String, Integer>();
			int size = contactList.size();

			for (int x = 0; x < size; x++) {
				String s = contactList.get(x).getContactName();

				if(s!=null && !TextUtils.isEmpty(s))
				{
					// get the first letter of the store
					String ch = s.substring(0, 1);
					// convert to uppercase otherwise lowercase a -z will be sorted
					// after upper A-Z
					ch = ch.toUpperCase();

					// HashMap will prevent duplicates
					alphaIndexer.put(ch, x);
				}
			}

			Set<String> sectionLetters = alphaIndexer.keySet();

			// create a list from the set to sort
			ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
			Collections.sort(sectionList);
			sections = new String[sectionList.size()];
			sectionList.toArray(sections);
		}

		@Override
		public Filter getFilter() {
			if (filter == null) {
				filter = new ContactFilter();
			}
			return filter;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (view == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.grd_item_contact, null);
			}
			final Contact contact = contactList.get(position);
			if (contact != null) {
				TextView displayName = (TextView) view.findViewById(R.id.grid_item_display_name);
				ImageView displayPicture = (ImageView) view.findViewById(R.id.grid_item_display_picture);
				TextView displayZname = (TextView) view.findViewById(R.id.grid_item_zname);
				ImageView imgCall = (ImageView) view.findViewById(R.id.grid_item_call);
				ImageView imgMsg = (ImageView) view.findViewById(R.id.grid_item_message);

				displayPicture.setImageURI(contact.getContactPhotoUri());

				if (displayPicture.getDrawable() == null)
					displayPicture.setImageResource(R.drawable.def_contact);

				displayName.setText(contact.getContactName());
				displayZname.setText(
						contact.getContactNumber().contains(",")
						?contact.getContactNumber().toString().substring(0, contact.getContactNumber().toString().indexOf(","))
						:contact.getContactNumber()
						);
				
				displayName.setTypeface(styleFont);
				displayZname.setTypeface(styleFont);
				
				view.setTag(R.id.TAG_CONTACT_NUMBER, contact.getContactNumber());
				view.setTag(R.id.TAG_CONTACT_DP, contact.getContactPhotoUri());
				view.setTag(R.id.TAG_CONTACT_NAME, contact.getContactName());
				
				imgCall.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent callIntent = new Intent(Intent.ACTION_DIAL);
				          callIntent.setData(Uri.parse("tel:"+Uri.encode(contact.getContactNumber())));
				          startActivity(callIntent);
					}
				});
				
				imgMsg.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+Uri.encode(contact.getContactNumber())));
			            startActivity(smsIntent);
					}
				});
			}
			return view;
		}

		@Override
		public int getPositionForSection(int section) {
			try {
				if (section > sections.length - 1) {
			        return 0;
			    } else {
			        return alphaIndexer.get(sections[section]);
			    }
			} catch (Exception e) {
				Log.e(TAG,e.toString());
				return 0;
			}
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			return sections;
		}

		// Contacts filter
		private class ContactFilter extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				constraint = constraint.toString().toLowerCase();
				FilterResults result = new FilterResults();
				if (constraint != null && constraint.toString().length() > 0) {
					ArrayList<Contact> filteredItems = new ArrayList<Contact>();

					for (int i = 0, l = originalList.size(); i < l; i++) {
						Contact contact = originalList.get(i);
						if (contact.toString().toLowerCase().contains(constraint))
							filteredItems.add(contact);
					}
					result.count = filteredItems.size();
					result.values = filteredItems;
				} else {
					synchronized (this) {
						result.values = originalList;
						result.count = originalList.size();
					}
				}
				return result;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {

				contactList = (ArrayList<Contact>) results.values;
				notifyDataSetChanged();
				clear();
				for (int i = 0, l = contactList.size(); i < l; i++)
					add(contactList.get(i));
				notifyDataSetInvalidated();
			}
		}
	}
	
}
