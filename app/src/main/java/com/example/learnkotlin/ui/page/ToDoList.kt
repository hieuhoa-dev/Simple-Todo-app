package com.example.learnkotlin.ui.page

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import kotlinx.coroutines.channels.Channel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnkotlin.R
import com.example.learnkotlin.data.Todo
import com.example.learnkotlin.logic.TodoViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import sh.calvin.reorderable.ReorderableCollectionItemScope
import sh.calvin.reorderable.rememberReorderableLazyListState
import sh.calvin.reorderable.ReorderableItem
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListPage(viewModel: TodoViewModel, innerPadding: PaddingValues) {
    val todos by viewModel.todoList.observeAsState(initial = emptyList())

    // Channel to synchronize list updates and prevent flickering
    val listUpdatedChannel = remember { Channel<Unit>(Channel.CONFLATED) }

    val lazyListState = rememberLazyListState()

    // Notify when the list is updated from the database
    LaunchedEffect(todos) {
        listUpdatedChannel.trySend(Unit)
    }

    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        // Clear any pending notifications
        listUpdatedChannel.tryReceive()

        // Update the list in the database
        viewModel.reorder(from.index, to.index)

        // Wait for the list to be updated from LiveData
        listUpdatedChannel.receive()
    }
    val hapticFeedback = LocalHapticFeedback.current

    var inputText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (todos.isEmpty()) {
                Text(
                    text = "No Todo",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp
                )
            } else {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(todos, key = { _, item -> item.id }) { _, item ->
                        ReorderableItem(reorderableLazyListState, key = item.id) { isDragging ->
                            val elevation by animateDpAsState(
                                if (isDragging) 50.dp else 0.dp,
                                label = "elevation"
                            )
                            val tonalElevation by animateDpAsState(
                                if (isDragging) 20.dp else 0.dp,
                                label = "tonalElevation"
                            )
                            Surface(
                                shadowElevation = elevation,
                                tonalElevation = tonalElevation,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                TodoItem(
                                    item = item,
                                    scope = this,
                                    hapticFeedback = hapticFeedback,
                                    onDelete = { viewModel.deleteTodo(item.id) }
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    inputText = it
                })
            IconButton(
                onClick = {
                    viewModel.addTodo(inputText)
                    inputText = ""
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    item: Todo,
    hapticFeedback: HapticFeedback,
    scope: ReorderableCollectionItemScope,
    onDelete: () -> Unit
) {
    val dateString = remember(item.createAt) {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(item.createAt)
    }

    val deleteTodo = SwipeAction(
        icon = painterResource(id = R.drawable.baseline_delete_24),
        background = Color.Red,
        onSwipe = onDelete
    )

    SwipeableActionsBox(
        endActions = listOf(deleteTodo),
        swipeThreshold = 40.dp,
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surfaceColorAtElevation(40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = with(scope) {
                    Modifier
                        .draggableHandle(
                            onDragStarted = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            onDragStopped = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                        )
                },
                onClick = {},
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.drag_handle_svgrepo_com),
                    contentDescription = "Reorder",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dateString,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
