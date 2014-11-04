package wys.Dialogs;




import com.wys.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class EmptyContentDialog extends Dialog {


	public EmptyContentDialog(Context context) {
		super(context);
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.empty_content_dialog);
	}

	
	
	
}
