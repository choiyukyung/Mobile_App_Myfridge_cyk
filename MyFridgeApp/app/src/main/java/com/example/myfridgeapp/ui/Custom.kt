package com.example.myfridgeapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfridgeapp.R
import com.example.myfridgeapp.ui.theme.MintBlue
import com.example.myfridgeapp.ui.theme.MintWhite
import com.example.myfridgeapp.ui.theme.fontMint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    iconId: Int? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        label = { Text(text = label) },
        leadingIcon = if (iconId != null) { // iconId가 null이 아닐 때만 leadingIcon을 추가
            {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = fontMint
                )
            }
        } else null,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MintWhite,
            disabledBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            unfocusedLabelColor = fontMint,
            focusedLabelColor = fontMint
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun CustomRegisterButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MintBlue),
        shape = RoundedCornerShape(10.dp),
        enabled = enabled
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_register),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Text(
            text = text,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}