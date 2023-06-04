package com.example.test1.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.test1.MainViewModel
import com.example.test1.R
import com.example.test1.ui.theme.TestTheme

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Consider using safe args plugin
        val userId = arguments?.getString("userId")
        viewModel.setUserId(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_profile, container, false)

        rootView.findViewById<ComposeView>(R.id.toolbar_compose_view).apply {
            setContent {
                profileSheet(modifier = Modifier.wrapContentSize())
            }
        }

        rootView.findViewById<ComposeView>(R.id.profile_compose_view).apply {
            setContent {
                val userData by viewModel.userData.observeAsState()
                val nestedScrollInteropConnection = rememberNestedScrollInteropConnection()

                TestTheme {
                    if(userData==null){
                        profileError()
                    }else{
                        profileShow(userData = userData!!, nestedScrollInteropConnection = nestedScrollInteropConnection)
                    }
                }
            }
        }

        return rootView
    }
}

@Composable
fun profileSheet(
    modifier: Modifier
) {
    Text(text = "perfil fragment")
}

@Composable
fun profileError(){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text="No hay perfil alv", color=MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun profileShow(userData: ProfileScreenState, nestedScrollInteropConnection: NestedScrollConnection= rememberNestedScrollInteropConnection()){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text="Hola ${userData.displayName}", color=MaterialTheme.colorScheme.secondary)
    }
}
