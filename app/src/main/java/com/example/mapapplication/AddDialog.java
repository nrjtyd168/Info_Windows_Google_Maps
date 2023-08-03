package com.example.mapapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddDialog extends AppCompatDialogFragment {

    TextView textView1, textView2, textView3, textView4;
    Spinner dropDownMenu1, dropDownMenu2;
    EditText editText1, editText2;
    ImageView imageView2;
    ArrayAdapter<String> adapter2;

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String coordinates = getArguments().getString("coordinates");
        String[] latlng = coordinates.split(",");
        String lat = latlng[0], lng = latlng[1], act = latlng[2];

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        String url = "http://your_ip_address:port_number/update_map.php";

        imageView2 = view.findViewById(R.id.imageView2);

        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);

        dropDownMenu1 = view.findViewById(R.id.dropdown_menu1);
        dropDownMenu2 = view.findViewById(R.id.dropdown_menu2);

        editText1 = view.findViewById(R.id.editText1);
        editText2 = view.findViewById(R.id.editText2);

        imageView2.setImageResource(R.drawable.location);
        textView1.setText("Tag Type : ");
        String[] tagType = {"Select", "Construction", "Hospitals", "Restaurants", "Toilets", "Weather"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tagType);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu1.setAdapter(adapter1);
        dropDownMenu1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (dropDownMenu1.getSelectedItem().toString().equals("Construction")) {
                    textView2.setText("Detail : ");

                    textView3.setText("Status : ");
                    String[] status = {"Select", "Completed", "Halted", "Incomplete", "In-Progress"};
                    adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, status);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropDownMenu2.setAdapter(adapter2);

                    textView4.setText("Description : ");
                } else if (dropDownMenu1.getSelectedItem().equals("Hospitals")) {
                    textView2.setText("Name : ");

                    textView3.setText("Speciality : ");
                    String[] speciality = {"Select", "Allergy and Immunology", "Anesthesiology", "Dermatology", "Diagnostic Radiology",
                            "Emergency Medicine", "Family Medicine", "Internal Medicine", "Medical Genetics", "MultiSpeciality",
                            "Neurology", "Obstetrics and Gynecology", "Ophthalmology", "Pathology", "Pediatrics", "Physical Medicine",
                            "Rehabilitation", "Preventive Medicine", "Psychiatry", "Radiation Oncology", "Surgery", "Urology"};
                    adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, speciality);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropDownMenu2.setAdapter(adapter2);

                    textView4.setText("Available : ");
                } else if (dropDownMenu1.getSelectedItem().toString().equals("Restaurants")) {
                    textView2.setText("Name : ");

                    textView3.setText("Type : ");
                    String[] type = {"Select", "Veg", "Non-Veg", "Veg & Non-Veg"};
                    adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, type);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropDownMenu2.setAdapter(adapter2);

                    textView4.setText("Available : ");
                } else if (dropDownMenu1.getSelectedItem().toString().equals("Toilets")) {
                    textView2.setText("Name : ");

                    textView3.setText("Gender : ");
                    String[] gender = {"Select", "Gents", "Ladies", "Ladies & Gents"};
                    adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, gender);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropDownMenu2.setAdapter(adapter2);

                    textView4.setText("Available : ");
                } else if (dropDownMenu1.getSelectedItem().toString().equals("Weather")) {
                    textView2.setText("Temperature : ");
                    editText1.setHint("Degree Celsius");

                    textView3.setText("Climate : ");
                    String[] climate = {"Select", "Clear", "Cloudy", "Rainy", "Stormy", "Sunny"};
                    adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, climate);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropDownMenu2.setAdapter(adapter2);

                    textView4.setText("Description : ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (act.equals("Add")) {
            builder.setTitle("Add Annotation");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            String[] status = response.split("~");
                                            if (status[0].equals("success")) {
                                                Toast.makeText(view.getContext(), "Tag Added Successfully..", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(view.getContext(), "Received Unexpected Data!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(view.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> paramV = new HashMap<>();
                                    paramV.put("general", "INSERT INTO tags (type, col1, col2, col3, lat, lng) VALUES ('" +
                                            dropDownMenu1.getSelectedItem() + "', '" + editText1.getText() + "', '" +
                                            dropDownMenu2.getSelectedItem() + "', '" + editText2.getText() + "', " +
                                            lat + ", " + lng + ")");
                                    return paramV;
                                }
                            };
                            queue.add(stringRequest);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        } else if (act.equals("Edit")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String[] status = response.split("~");
                            if (status[0].equals("success")) {
                                String[] content = status[1].split("`");
                                dropDownMenu1.setSelection(adapter1.getPosition(content[0]));
                                editText1.setText(content[1]);
                                dropDownMenu2.setSelection(adapter2.getPosition(content[2]));
                                editText2.setText(content[3]);
                            } else
                                Toast.makeText(view.getContext(), "Received Unexpected Data!!", Toast.LENGTH_SHORT).show();
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(view.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("show", "SELECT * FROM tags WHERE lat = '" + lat + "' AND lng = '" + lng + "'");
                    return paramV;
                }
            };
            queue.add(stringRequest);
            builder.setTitle("Edit Annotation");
            builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            String[] status = response.split("~");
                                            if (status[0].equals("success")) {
                                                Toast.makeText(view.getContext(), "Tag Updated Successfully..", Toast.LENGTH_LONG).show();
                                            } else
                                                Toast.makeText(view.getContext(), "Received Unexpected Data!!", Toast.LENGTH_LONG).show();
                                        }
                                    }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(view.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> paramV = new HashMap<>();
                                    paramV.put("general", "UPDATE tags  SET type = '" + dropDownMenu1.getSelectedItem() +
                                            "', col1 = '" + editText1.getText() + "', col2 = '" + dropDownMenu2.getSelectedItem() +
                                            "', col3 = '" + editText2.getText() + "' WHERE lat = '" + lat + "' AND lng = '" + lng + "'");
                                    return paramV;
                                }
                            };
                            queue.add(stringRequest);
                        }
                    })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            String[] status = response.split("~");
                                            if (status[0].equals("success")) {
                                                Toast.makeText(view.getContext(), "Tag Deleted Successfully..", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(view.getContext(), "Received Unexpected Data!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(view.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> paramV = new HashMap<>();
                                    paramV.put("general", "DELETE FROM tags WHERE lat = '" + lat + "' AND lng = '" + lng + "'");
                                    return paramV;
                                }
                            };
                            queue.add(stringRequest);
                        }
                    });
        }
        return builder.create();
    }

}
