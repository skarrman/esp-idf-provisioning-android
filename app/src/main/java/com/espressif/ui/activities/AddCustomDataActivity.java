package com.espressif.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.espressif.AppConstants;
import com.espressif.wifi_provisioning.R;

public class AddCustomDataActivity extends AppCompatActivity {

    private EditText
            ota_uri,
            ota_username,
            ota_password,
            mqtt_uri,
            cognito_id,
            client_cert,
            client_key,
            client_id;

    private String ssid, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_custom_data);

        Intent intent = getIntent();
        ssid = intent.getStringExtra(AppConstants.KEY_WIFI_SSID);
        password = intent.getStringExtra(AppConstants.KEY_WIFI_PASSWORD);

        ota_uri = findViewById(R.id.et_ota_uri_input);
        ota_username = findViewById(R.id.et_ota_username_input);
        ota_password = findViewById(R.id.et_ota_password_input);
        mqtt_uri = findViewById(R.id.et_mqtt_uri_input);
        cognito_id = findViewById(R.id.et_cognito_id_input);
        client_cert = findViewById(R.id.et_client_cert_input);
        client_key = findViewById(R.id.et_client_key_input);
        client_id = findViewById(R.id.et_client_id_input);

        CardView btnNext = findViewById(R.id.btn_next);
        TextView txtNextBtn = findViewById(R.id.text_btn);
        txtNextBtn.setText(R.string.btn_next);
        btnNext.setOnClickListener(nextBtnClickListener);
    }

    // TODO: Add device event listener

    private final View.OnClickListener nextBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String otaUri = ota_uri.getText().toString();
            String otaUsername = ota_username.getText().toString();
            String otaPassword = ota_password.getText().toString();
            String mqttUri = mqtt_uri.getText().toString();
            String cognitoId = cognito_id.getText().toString();
            String clientCert = client_cert.getText().toString();
            String clientKey = client_key.getText().toString();
            String clientId = client_id.getText().toString();

            ProvisionActivity.CustomData[] customData = new ProvisionActivity.CustomData[]{
                    new ProvisionActivity.CustomData("ota-uri", otaUri),
                    new ProvisionActivity.CustomData("ota-username", otaUsername),
                    new ProvisionActivity.CustomData("ota-password", otaPassword),
                    new ProvisionActivity.CustomData("mqtt-uri", mqttUri),
                    new ProvisionActivity.CustomData("cognito-id", cognitoId),
                    new ProvisionActivity.CustomData("client-cert", clientCert),
                    new ProvisionActivity.CustomData("client-key", clientKey),
                    new ProvisionActivity.CustomData("client-id", clientId),
            };

            goToProvisionActivity(ssid, password, customData);
        }
    };

    private void goToProvisionActivity(String ssid, String password, ProvisionActivity.CustomData[] customData) {

        finish();
        Intent provisionIntent = new Intent(getApplicationContext(), ProvisionActivity.class);
        provisionIntent.putExtras(getIntent());
        provisionIntent.putExtra(AppConstants.KEY_WIFI_SSID, ssid);
        provisionIntent.putExtra(AppConstants.KEY_WIFI_PASSWORD, password);
        provisionIntent.putExtra(AppConstants.KEY_CUSTOM_DATA, customData);
        startActivity(provisionIntent);
    }
}
