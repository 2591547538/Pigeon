
package com.pigeon.communication.privacy;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;



public class ChatBottomView extends LinearLayout{
	private View baseView;
	private LinearLayout imageGroup;
	private LinearLayout cameraGroup;
	private LinearLayout phraseGroup;
	private LinearLayout phrasevoide;
	private HeadIconSelectorView.OnHeadIconClickListener onHeadIconClickListener;
	public static final int FROM_CAMERA = 1;
	public static final int FROM_GALLERY = 2;
	public static final int FROM_PHRASE = 3;
	public static final int FROM_VOIDE = 6;
	public ChatBottomView(Context context, AttributeSet attrs) {
		super(context, attrs);

		findView();
		init();
	}
	
	private void findView(){
		baseView = LayoutInflater.from(getContext()).inflate(R.layout.layout_tongbaobottom, this);
		imageGroup = (LinearLayout) baseView.findViewById(R.id.image_bottom_group);
		cameraGroup = (LinearLayout) baseView.findViewById(R.id.camera_group);
		phraseGroup = (LinearLayout) baseView.findViewById(R.id.phrase_group);
		phrasevoide = (LinearLayout) baseView.findViewById(R.id.voide);
	}
	private void init(){
		cameraGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != onHeadIconClickListener) {
					onHeadIconClickListener.onClick(FROM_CAMERA);
				}
			}
		});
		imageGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (null != onHeadIconClickListener) {
					onHeadIconClickListener.onClick(FROM_GALLERY);
				}
			}
		});
		phraseGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (null != onHeadIconClickListener) {
					onHeadIconClickListener.onClick(FROM_PHRASE);
				}
			}
		});
		phrasevoide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (null != onHeadIconClickListener) {
					onHeadIconClickListener.onClick(FROM_VOIDE);
				}
			}
		});
	}

	public void setOnHeadIconClickListener(
			HeadIconSelectorView.OnHeadIconClickListener onHeadIconClickListener) {

		this.onHeadIconClickListener = onHeadIconClickListener;
	}
}
