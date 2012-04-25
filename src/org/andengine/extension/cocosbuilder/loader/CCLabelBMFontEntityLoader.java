package org.andengine.extension.cocosbuilder.loader;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.extension.cocosbuilder.CCBEntityLoaderDataSource;
import org.andengine.extension.cocosbuilder.entity.CCLabelBMFont;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.content.res.AssetManager;

/**
 * (c) Zynga 2012
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 14:44:04 - 25.04.2012
 */
public class CCLabelBMFontEntityLoader extends CCLabelEntityLoader {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final String ENTITY_NAMES = "CCLabelBMFont";

	private static final String TAG_CCLABELBMFONT_ATTRIBUTE_FONT_NAME = "fontName";

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public CCLabelBMFontEntityLoader() {
		super(CCLabelBMFontEntityLoader.ENTITY_NAMES);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected IFont getFont(final Attributes pAttributes, final CCBEntityLoaderDataSource pCCBEntityLoaderDataSource) throws IOException {
		final String fontName = SAXUtils.getAttributeOrThrow(pAttributes, CCLabelBMFontEntityLoader.TAG_CCLABELBMFONT_ATTRIBUTE_FONT_NAME);

		final String fontID = CCLabelEntityLoader.getFontID(fontName);

		final FontManager fontManager = pCCBEntityLoaderDataSource.getFontManager();
		if(fontManager.hasMappedFont(fontID)) {
			return fontManager.getMappedFont(fontID);
		} else {
			final TextureManager textureManager = pCCBEntityLoaderDataSource.getTextureManager();
			final AssetManager assetManager = pCCBEntityLoaderDataSource.getAssetManager();

			final String fontPath = pCCBEntityLoaderDataSource.getAssetBasePath() + fontName;
			final BitmapFont font = new BitmapFont(textureManager, assetManager, fontPath);
			font.load();

			fontManager.addMappedFont(fontID, font);

			return font;
		}
	}

	@Override
	protected IEntity createCCLabel(final float pX, final float pY, final IFont pFont, final CharSequence pText, final CCBEntityLoaderDataSource pCCBEntityLoaderDataSource) throws IOException {
		return new CCLabelBMFont(pX, pY, pFont, pText, pCCBEntityLoaderDataSource.getVertexBufferObjectManager());
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
