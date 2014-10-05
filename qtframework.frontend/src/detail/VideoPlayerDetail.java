package detail;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QContextMenuEvent;
import com.trolltech.qt.gui.QCursor;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QProgressBar;
import com.trolltech.qt.gui.QToolBar;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.phonon.AudioOutput;
import com.trolltech.qt.phonon.MediaObject;
import com.trolltech.qt.phonon.MediaSource;
import com.trolltech.qt.phonon.Phonon;
import com.trolltech.qt.phonon.VideoWidget;
import component.AbstractDockWidget;

import config.IComponentConfiguration;

public class VideoPlayerDetail extends AbstractDockWidget
{
	private VideoWidget videoWidget;
	private MediaObject object;
	private final QToolBar toolBar;

	private QAction playAction, pauseAction, stopAction;
	private QMenu contextMenu;
	private QProgressBar prograssbar;
	private Long lengthOfMedia;
	private Long temp;
	private QAction chooseFileAction;
	private final QMenu chooserMenu;

	public VideoPlayerDetail(final IComponentConfiguration config, final QToolBar toolBar, final QMenuBar menuBar)
	{
		super(config);
		this.toolBar = toolBar;

		setWindowTitle("Video Player");
		setWhatsThis("A <b>Video</b> Player");

		createActions();

		chooserMenu = new QMenu(tr("Video"));
		chooserMenu.addAction(chooseFileAction);
		menuBar.addMenu(chooserMenu);
	}

	@Override
	public QWidget getContent()
	{
		prograssbar = new QProgressBar();

		object = new MediaObject();
		object.setTickInterval(100);
		object.tick.connect(this, "valueChanged(Long)");

		final AudioOutput output = new AudioOutput(Phonon.Category.MusicCategory, this);
		output.setVolume(0.9);

		videoWidget = new VideoWidget();
		Phonon.createPath(object, videoWidget);
		Phonon.createPath(object, output);

		final QWidget content = new QWidget();

		final QGridLayout layout = new QGridLayout(content);
		layout.addWidget(videoWidget, 0, 0);
		layout.addWidget(prograssbar, 1, 0);

		return content;
	}


	@Override
	public void visibilityChanged()
	{
		super.visibilityChanged();
		if(!visible)
		{
			toolBar.removeAction(playAction);
			toolBar.removeAction(pauseAction);
			toolBar.removeAction(stopAction);

			chooserMenu.removeAction(playAction);
			chooserMenu.removeAction(pauseAction);
			chooserMenu.removeAction(stopAction);
		}
		else {
			toolBar.addAction(playAction);
			toolBar.addAction(pauseAction);
			toolBar.addAction(stopAction);

			chooserMenu.addAction(playAction);
			chooserMenu.addAction(pauseAction);
			chooserMenu.addAction(stopAction);
		}
	}

	@Override
	protected void loadData() {
	}

	private void createActions()
	{
		playAction = new QAction(toolBar);
		playAction.setIcon(new QIcon("classpath:icon/media_play.png"));
		playAction.setText("Play");
		playAction.triggered.connect(this, "play()");
		playAction.setToolTip("Startet das Video");

		pauseAction = new QAction(toolBar);
		pauseAction.setIcon(new QIcon("classpath:icon/media_pause.png"));
		pauseAction.setText("Pause");
		pauseAction.triggered.connect(this, "pause()");
		pauseAction.setToolTip("Pausiert das Video");
		pauseAction.setEnabled(false);

		stopAction = new QAction(toolBar);
		stopAction.setIcon(new QIcon("classpath:icon/media_stop.png"));
		stopAction.setText("Stop");
		stopAction.triggered.connect(this, "stop()");
		stopAction.setToolTip("Stoppt das Video");
		stopAction.setEnabled(false);

		contextMenu = new QMenu();
		chooseFileAction = new QAction(toolBar);
		chooseFileAction.setText("Datei...");
		chooseFileAction.triggered.connect(this, "setMultimediaFile()");

		contextMenu.addAction(chooseFileAction);
	}

	@Override
	protected void contextMenuEvent(final QContextMenuEvent arg) {
		super.contextMenuEvent(arg);
		contextMenu.exec(QCursor.pos());
	};

	/**
	 * SLOTS
	 */

	@SuppressWarnings("unused")
	private void play() {
		object.play();
		playAction.setEnabled(false);
		stopAction.setEnabled(true);
		pauseAction.setEnabled(true);
	}

	@SuppressWarnings("unused")
	private void stop() {
		object.stop();
		playAction.setEnabled(true);
		stopAction.setEnabled(false);
		pauseAction.setEnabled(false);
	}

	@SuppressWarnings("unused")
	private void pause() {
		object.pause();
		playAction.setEnabled(true);
		stopAction.setEnabled(true);
		pauseAction.setEnabled(false);
	}

	@SuppressWarnings("unused")
	private void setTotalTime(final Long time) {
		lengthOfMedia = time;
	}

	@SuppressWarnings("unused")
	private void valueChanged(final Long mllsec)
	{
		if(mllsec != null) {
			temp = mllsec / (lengthOfMedia / 100);
		}

		prograssbar.setValue(temp.intValue());
	}

	@SuppressWarnings("unused")
	private void setMultimediaFile() {
		final QFileDialog fileDialog = new QFileDialog();
		if(fileDialog.exec() != 0) {
			object.setCurrentSource(new MediaSource(fileDialog.selectedFiles().get(0)));
			object.totalTimeChanged.connect(this, "setTotalTime(Long)");
		}
	}
}
