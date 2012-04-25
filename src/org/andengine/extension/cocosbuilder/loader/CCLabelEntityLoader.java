package org.andengine.extension.cocosbuilder.loader;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.entity.text.Text;
import org.andengine.extension.cocosbuilder.CCBEntityLoaderDataSource;
import org.andengine.opengl.font.IFont;
import org.andengine.util.SAXUtils;
import org.andengine.util.align.HorizontalAlign;
import org.andengine.util.align.VerticalAlign;
import org.xml.sax.Attributes;

/**
 * (c) Zynga 2012
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 14:44:04 - 25.04.2012
 */
public abstract class CCLabelEntityLoader extends CCNodeEntityLoader {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final String TAG_CCLABEL_ATTRIBUTE_TEXT = "text";

	private static final String TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL = "horizontalAlign";
	private static final int TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL_VALUE_LEFT = 0;
	private static final int TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL_VALUE_CENTER = 1;
	private static final int TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL_VALUE_RIGHT = 2;
	private static final String TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL = "verticalAlign";
	private static final int TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL_VALUE_TOP = 0;
	private static final int TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL_VALUE_CENTER = 1;
	private static final int TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL_VALUE_BOTTOM = 2;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	protected CCLabelEntityLoader(final String ... pEntityNames) {
		super(pEntityNames);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected abstract IFont getFont(final Attributes pAttributes, final CCBEntityLoaderDataSource pCCBEntityLoaderDataSource) throws IOException;
	protected abstract IEntity createCCLabel(float pX, float pY, final IFont font, final CharSequence text, CCBEntityLoaderDataSource pCCBEntityLoaderDataSource) throws IOException;

	@Override
	protected IEntity createEntity(String pEntityName, float pX, float pY, float pWidth, float pHeight, Attributes pAttributes, CCBEntityLoaderDataSource pCCBEntityLoaderDataSource) throws IOException {
		final IFont font = this.getFont(pAttributes, pCCBEntityLoaderDataSource);
		final CharSequence text = this.getText(pAttributes);

		return this.createCCLabel(pX, pY, font, text, pCCBEntityLoaderDataSource);
	}

	@Override
	protected void setAttributes(final IEntity pEntity, Attributes pAttributes) {
		super.setAttributes(pEntity, pAttributes);

		this.setCCLabelAttributes((Text)pEntity, pAttributes);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	protected void setCCLabelAttributes(final Text pText, final Attributes pAttributes) {
		this.setCCLabelBlendFunction(pText, pAttributes);
		this.setCCLabelHorizontalAlign(pText, pAttributes);
	}


	protected void setCCLabelBlendFunction(final Text pText, final Attributes pAttributes) {
		pText.setBlendFunction(this.getBlendFunctionSource(pAttributes), this.getBlendFunctionDestination(pAttributes));
	}

	protected void setCCLabelHorizontalAlign(final Text pText, final Attributes pAttributes) {
		final int horizontalAlignValue = SAXUtils.getIntAttributeOrThrow(pAttributes, CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL);
		final HorizontalAlign horizontalAlign;
		switch(horizontalAlignValue) {
			case CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL_VALUE_LEFT:
				horizontalAlign = HorizontalAlign.LEFT;
				break;
			case CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL_VALUE_CENTER:
				horizontalAlign = HorizontalAlign.CENTER;
				break;
			case CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL_VALUE_RIGHT:
				horizontalAlign = HorizontalAlign.RIGHT;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value for '" + CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_HORIZONTAL +"': '" + horizontalAlignValue + "'.");
		}
		pText.setHorizontalAlign(horizontalAlign);
	}

	protected void setCCLabelVerticalAlign(final Text pText, final Attributes pAttributes) {
		final int verticalAlignValue = SAXUtils.getIntAttributeOrThrow(pAttributes, CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL);
		final VerticalAlign verticalAlign;
		switch(verticalAlignValue) {
			case CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL_VALUE_TOP:
				verticalAlign = VerticalAlign.TOP;
				break;
			case CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL_VALUE_CENTER:
				verticalAlign = VerticalAlign.CENTER;
				break;
			case CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL_VALUE_BOTTOM:
				verticalAlign = VerticalAlign.BOTTOM;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value for '" + CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_ALIGN_VERTICAL +"': '" + verticalAlignValue + "'.");
		}

		// TODO Vertical Align?
//		pText.setVerticalAlign(verticalAlign);
	}

	protected CharSequence getText(final Attributes pAttributes) {
		return SAXUtils.getAttributeOrThrow(pAttributes, CCLabelEntityLoader.TAG_CCLABEL_ATTRIBUTE_TEXT);
	}

	protected static String getFontID(final String pFontName) {
		return pFontName + "@" + "NOSIZE";
	}

	protected static String getFontID(final String pFontName, final float pFontSize) {
		return pFontName + "@" + pFontSize;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
