package dev.illwiz.tada

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * To be able to tests Fragment using FragmentScenario
 * Because Hilt Fragment need to be attached to Activity
 */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()
