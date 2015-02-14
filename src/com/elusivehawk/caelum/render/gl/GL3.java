
package com.elusivehawk.caelum.render.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL40;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class GL3
{
	private GL3(){}
	
	public static void glBeginConditionalRender(int id, int mode) throws GLException
	{
		GL30.glBeginConditionalRender(id, mode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBeginTransformFeedback(int primitiveMode) throws GLException
	{
		GL30.glBeginTransformFeedback(primitiveMode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindBufferBase(int target, int index, int buffer) throws GLException
	{
		GL30.glBindBufferBase(target, index, buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindBufferRange(int target, int index, int buffer, long offset, long size) throws GLException
	{
		GL30.glBindBufferRange(target, index, buffer, offset, size);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindFragDataLocation(int program, int colorNumber, String name) throws GLException
	{
		GL30.glBindFragDataLocation(program, colorNumber, name);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, String name) throws GLException
	{
		GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindFramebuffer(GLEnumFBType target, int framebuffer)
	{
		glBindFramebuffer(target.gl, framebuffer);
		
	}
	
	public static void glBindFramebuffer(int target, int framebuffer) throws GLException
	{
		GL30.glBindFramebuffer(target, framebuffer);
		
	}
	
	public static void glBindRenderbuffer(int renderbuffer) throws GLException
	{
		glBindRenderbuffer(GLConst.GL_RENDERBUFFER, renderbuffer);
		
	}
	
	public static void glBindRenderbuffer(int target, int renderbuffer) throws GLException
	{
		GL30.glBindRenderbuffer(target, renderbuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindSampler(int unit, int sampler) throws GLException
	{
		GL33.glBindSampler(unit, sampler);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindVertexArray(GLVertexArray array) throws GLException
	{
		glBindVertexArray(array == null ? 0 : array.getId());
		
	}
	
	public static void glBindVertexArray(int array) throws GLException
	{
		GL30.glBindVertexArray(array);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) throws GLException
	{
		GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glCheckFramebufferStatus(GLEnumFBType target) throws GLException
	{
		return glCheckFramebufferStatus(target.gl);
	}
	
	public static int glCheckFramebufferStatus(int target) throws GLException
	{
		int ret = GL30.glCheckFramebufferStatus(target);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glClampColor(int target, int clamp) throws GLException
	{
		GL30.glClampColor(target, clamp);
		
	}
	
	public static void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) throws GLException
	{
		GL30.glClearBufferfi(buffer, drawbuffer, depth, stencil);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glClientWaitSync(long sync, int flags, long timeout) throws GLException
	{
		int ret = GL32.glClientWaitSync(sync, flags, timeout);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a) throws GLException
	{
		GL30.glColorMaski(buf, r, g, b, a);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size) throws GLException
	{
		GL31.glCopyBufferSubData(readtarget, writetarget, readoffset, writeoffset, size);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteFramebuffer(int framebuffer) throws GLException
	{
		GL30.glDeleteFramebuffers(framebuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteFramebuffers(IntBuffer framebuffers) throws GLException
	{
		GL30.glDeleteFramebuffers(framebuffers);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteRenderbuffer(int renderbuffer) throws GLException
	{
		GL30.glDeleteRenderbuffers(renderbuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteRenderbuffers(IntBuffer renderbuffer) throws GLException
	{
		GL30.glDeleteRenderbuffers(renderbuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteSampler(int sampler) throws GLException
	{
		GL33.glDeleteSamplers(sampler);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteSamplers(IntBuffer samplers) throws GLException
	{
		GL33.glDeleteSamplers(samplers);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteSync(long sync) throws GLException
	{
		GL32.glDeleteSync(sync);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteVertexArray(int array) throws GLException
	{
		GL30.glDeleteVertexArrays(array);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteVertexArrays(IntBuffer arrays) throws GLException
	{
		GL30.glDeleteVertexArrays(arrays);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDisablei(int target, int index) throws GLException
	{
		GL30.glDisablei(target, index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawArraysInstanced(int mode, int first, int argcount, int primcount) throws GLException
	{
		GL31.glDrawArraysInstanced(mode, first, argcount, primcount);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException
	{
		GL32.glDrawElementsBaseVertex(mode, indices_count, type, indices_buffer_offset, basevertex);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset) throws GLException
	{
		GL40.glDrawElementsIndirect(mode, type, indirect_buffer_offset);//TODO Move to GL4
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount) throws GLException
	{
		GL31.glDrawElementsInstanced(mode, indices_count, type, indices_buffer_offset, primcount);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex) throws GLException
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices_count, type, indices_buffer_offset, primcount, basevertex);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices_count, type, indices_buffer_offset, basevertex);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEnablei(int target, int index) throws GLException
	{
		GL30.glEnablei(target, index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEndConditionalRender() throws GLException
	{
		GL30.glEndConditionalRender();
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEndTransformFeedback() throws GLException
	{
		GL30.glEndTransformFeedback();
		
		RenderHelper.checkForGLError();
		
	}
	
	public static long glFenceSync(int condition, int flags) throws GLException
	{
		long ret = GL32.glFenceSync(condition, flags);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glFlushMappedBufferRange(int target, long offset, long length) throws GLException
	{
		GL30.glFlushMappedBufferRange(target, offset, length);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFramebufferRenderbuffer(GLEnumFBType target, GLEnumFBAttach attachment, int renderbuffer) throws GLException
	{
		glFramebufferRenderbuffer(target, attachment, 0, renderbuffer);
		
	}
	
	public static void glFramebufferRenderbuffer(GLEnumFBType target, GLEnumFBAttach attachment, int count, int renderbuffer) throws GLException
	{
		glFramebufferRenderbuffer(target.gl, attachment.gl + count, GLConst.GL_RENDERBUFFER, renderbuffer);
		
	}
	
	public static void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) throws GLException
	{
		GL30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFramebufferTexture(GLEnumFBType target, GLEnumFBAttach attachment, int texture, int level) throws GLException
	{
		glFramebufferTexture(target, attachment, 0, texture, level);
		
	}
	
	public static void glFramebufferTexture(GLEnumFBType target, GLEnumFBAttach attachment, int count, int texture, int level) throws GLException
	{
		glFramebufferTexture(target.gl, attachment.gl + count, texture, level);
		
	}
	
	public static void glFramebufferTexture(int target, int attachment, int texture, int level) throws GLException
	{
		GL32.glFramebufferTexture(target, attachment, texture, level);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFramebufferTexture1D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture1D(target, attachment, 0, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture1D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture1D(target.gl, attachment.gl + count, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level) throws GLException
	{
		GL30.glFramebufferTexture1D(target, attachment, textarget, texture, level);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFramebufferTexture2D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture2D(target, attachment, 0, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture2D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture2D(target.gl, attachment.gl + count, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) throws GLException
	{
		GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFramebufferTexture3D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level, int zoffset) throws GLException
	{
		glFramebufferTexture3D(target, attachment, 0, textarget, texture, level, zoffset);
		
	}
	
	public static void glFramebufferTexture3D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level, int zoffset) throws GLException
	{
		glFramebufferTexture3D(target.gl, attachment.gl + count, textarget, texture, level, zoffset);
		
	}
	
	public static void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset) throws GLException
	{
		GL30.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) throws GLException
	{
		GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGenerateMipmap(GLEnumTexture target) throws GLException
	{
		glGenerateMipmap(target.gl);
		
	}
	
	public static void glGenerateMipmap(int target) throws GLException
	{
		GL30.glGenerateMipmap(target);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGenFramebuffer() throws GLException
	{
		int ret = GL30.glGenFramebuffers();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGenFramebuffers(int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGenFramebuffers(ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGenRenderbuffer() throws GLException
	{
		int ret = GL30.glGenRenderbuffers();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGenRenderbuffers(int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGenRenderbuffers(ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGenSampler() throws GLException
	{
		int ret = GL33.glGenSamplers();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGenSamplers(int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL33.glGenSamplers(ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGenVertexArray() throws GLException
	{
		int ret = GL30.glGenVertexArrays();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGenVertexArrays(int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGenVertexArrays(ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize) throws GLException
	{
		String ret = GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetActiveUniformName(int program, int uniformIndex, int bufSize) throws GLException
	{
		String ret = GL31.glGetActiveUniformName(program, uniformIndex, bufSize);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetActiveUniforms(int program, IntBuffer uniformIndices, int pname) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(uniformIndices.capacity());
		
		GL31.glGetActiveUniforms(program, uniformIndices, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetActiveUniformsi(int program, int uniformIndex, int pname) throws GLException
	{
		int ret = GL31.glGetActiveUniformsi(program, uniformIndex, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static boolean glGetBoolean(int value, int index) throws GLException
	{
		boolean ret = GL30.glGetBooleani(value, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static ByteBuffer glGetBoolean(int value, int index, int argcount) throws GLException
	{
		ByteBuffer ret = BufferHelper.createByteBuffer(argcount);
		
		GL30.glGetBooleani(value, index, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static LongBuffer glGetBufferParameter(int target, int pname, int argcount) throws GLException
	{
		LongBuffer ret = BufferHelper.createLongBuffer(argcount);
		
		GL32.glGetBufferParameter(target, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetBufferParameteri64(int target, int pname) throws GLException
	{
		long ret = GL32.glGetBufferParameteri64(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetFragDataIndex(int program, String name) throws GLException
	{
		int ret = GL33.glGetFragDataIndex(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetFragDataLocation(int program, String name) throws GLException
	{
		int ret = GL30.glGetFragDataLocation(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetFramebufferAttachmentParameter(int target, int attachment, int pname, int argcount)
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetFramebufferAttachmentParameter(target, attachment, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname) throws GLException
	{
		int ret = GL30.glGetFramebufferAttachmentParameteri(target, attachment, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetIntegeri(int value, int index) throws GLException
	{
		int ret = GL30.glGetIntegeri(value, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetInteger(int value, int index, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetInteger(value, index, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetInteger64(int pname) throws GLException
	{
		long ret = GL32.glGetInteger64(pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetInteger64i(int value, int index) throws GLException
	{
		long ret = GL32.glGetInteger64i(value, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static LongBuffer glGetInteger64(int value, int index, int argcount) throws GLException
	{
		LongBuffer ret = BufferHelper.createLongBuffer(argcount);
		
		GL32.glGetInteger64i(value, index, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static LongBuffer glGetInteger64(int pname, int argcount) throws GLException
	{
		LongBuffer ret = BufferHelper.createLongBuffer(argcount);
		
		GL32.glGetInteger64(pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static FloatBuffer glGetMultisample(int pname, int index, int argcount) throws GLException
	{
		FloatBuffer ret = BufferHelper.createFloatBuffer(argcount);
		
		GL32.glGetMultisample(pname, index, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static LongBuffer glGetQueryObject(int id, int pname, int argcount) throws GLException
	{
		LongBuffer ret = BufferHelper.createLongBuffer(argcount);
		
		GL33.glGetQueryObject(id, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetQueryObjecti64(int id, int pname) throws GLException
	{
		long ret = GL33.glGetQueryObjecti64(id, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetQueryObjectui64(int id, int pname) throws GLException
	{
		long ret = GL33.glGetQueryObjectui64(id, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetRenderbufferParameter(int target, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetRenderbufferParameter(target, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetRenderbufferParameteri(int target, int pname) throws GLException
	{
		int ret = GL30.glGetRenderbufferParameteri(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static FloatBuffer glGetSamplerParameter(int sampler, int pname, int argcount) throws GLException
	{
		FloatBuffer ret = BufferHelper.createFloatBuffer(argcount);
		
		GL33.glGetSamplerParameter(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetSamplerParameteri(int sampler, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL33.glGetSamplerParameter(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static float glGetSamplerParameterf(int sampler, int pname) throws GLException
	{
		float ret = GL33.glGetSamplerParameterf(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSamplerParameteri(int sampler, int pname) throws GLException
	{
		int ret = GL33.glGetSamplerParameteri(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetSamplerParameterI(int sampler, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL33.glGetSamplerParameterI(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSamplerParameterIi(int sampler, int pname) throws GLException
	{
		int ret = GL33.glGetSamplerParameterIi(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetSamplerParameterIu(int sampler, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL33.glGetSamplerParameterIu(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSamplerParameterIui(int sampler, int pname) throws GLException
	{
		int ret = GL33.glGetSamplerParameterIui(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetStringi(int name, int index) throws GLException
	{
		String ret = GL30.glGetStringi(name, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetSync(long sync, int pname, IntBuffer length) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(length.capacity());
		
		GL32.glGetSync(sync, pname, length, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSynci(long sync, int pname, IntBuffer length) throws GLException
	{
		int ret = GL32.glGetSynci(sync, pname, length);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetTexParameterI(int target, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetTexParameterI(target, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetTexParameterIi(int target, int pname) throws GLException
	{
		int ret = GL30.glGetTexParameterIi(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetTexParameterIu(int target, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetTexParameterIu(target, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetTexParameterIui(int target, int pname) throws GLException
	{
		int ret = GL30.glGetTexParameterIui(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetTransformFeedbackVarying(int program, int index, int bufSize, IntBuffer size, IntBuffer type) throws GLException
	{
		String ret = GL30.glGetTransformFeedbackVarying(program, index, bufSize, size, type);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetUniformBlockIndex(int program, String uniformBlockName) throws GLException
	{
		int ret = GL31.glGetUniformBlockIndex(program, uniformBlockName);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetUniformIndices(int program, String[] uniformNames) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(uniformNames.length);
		
		GL31.glGetUniformIndices(program, uniformNames, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetUniformu(int program, int location, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetUniformu(program, location, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetVertexAttribI(int index, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetVertexAttribI(index, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGetVertexAttribIu(int index, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glGetVertexAttribIu(index, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static boolean glIsEnabledi(int target, int index) throws GLException
	{
		return GL30.glIsEnabledi(target, index);
	}
	
	public static boolean glIsFramebuffer(int framebuffer)
	{
		return GL30.glIsFramebuffer(framebuffer);
	}
	
	public static boolean glIsRenderbuffer(int renderbuffer)
	{
		return GL30.glIsRenderbuffer(renderbuffer);
	}
	
	public static boolean glIsSampler(int sampler)
	{
		return GL33.glIsSampler(sampler);
	}
	
	public static boolean glIsSync(long sync)
	{
		return GL32.glIsSync(sync);
	}
	
	public static boolean glIsVertexArray(int array)
	{
		return GL30.glIsVertexArray(array);
	}
	
	public static ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer old_buffer) throws GLException
	{
		ByteBuffer ret = GL30.glMapBufferRange(target, offset, length, access, old_buffer);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glPrimitiveRestartIndex(int index)
	{
		GL31.glPrimitiveRestartIndex(index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glProvokingVertex(int mode) throws GLException
	{
		GL32.glProvokingVertex(mode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glQueryCounter(int id, int target) throws GLException
	{
		GL33.glQueryCounter(id, target);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glRenderbufferStorage(int internalformat, int width, int height) throws GLException
	{
		glRenderbufferStorage(GLConst.GL_RENDERBUFFER, internalformat, width, height);
		
	}
	
	public static void glRenderbufferStorage(int target, int internalformat, int width, int height) throws GLException
	{
		GL30.glRenderbufferStorage(target, internalformat, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height) throws GLException
	{
		GL30.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glSampleMaski(int index, int mask) throws GLException
	{
		GL32.glSampleMaski(index, mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static FloatBuffer glSamplerParameterf(int sampler, int pname, int argcount) throws GLException
	{
		FloatBuffer ret = BufferHelper.createFloatBuffer(argcount);
		
		GL33.glSamplerParameter(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glSamplerParameter(int sampler, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL33.glSamplerParameter(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glSamplerParameterf(int sampler, int pname, float param) throws GLException
	{
		GL33.glSamplerParameterf(sampler, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glSamplerParameteri(int sampler, int pname, int param) throws GLException
	{
		GL33.glSamplerParameteri(sampler, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static IntBuffer glSamplerParameterI(int sampler, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL33.glSamplerParameterI(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glSamplerParameterIu(int sampler, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL33.glSamplerParameterIu(sampler, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glTexBuffer(int target, int internalformat, int buffer) throws GLException
	{
		GL31.glTexBuffer(target, internalformat, buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) throws GLException
	{
		GL32.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) throws GLException
	{
		GL32.glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static IntBuffer glTexParameterI(int target, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glTexParameterI(target, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glTexParameterIi(int target, int pname, int param) throws GLException
	{
		GL30.glTexParameterIi(target, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static IntBuffer glTexParameterIu(int target, int pname, int argcount) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(argcount);
		
		GL30.glTexParameterIu(target, pname, ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glTexParameterIui(int target, int pname, int param) throws GLException
	{
		GL30.glTexParameterIui(target, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTransformFeedbackVaryings(int program, String[] varyings, int bufferMode) throws GLException
	{
		GL30.glTransformFeedbackVaryings(program, varyings, bufferMode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) throws GLException
	{
		GL31.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribDivisor(int index, int divisor) throws GLException
	{
		GL33.glVertexAttribDivisor(index, divisor);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI1(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1i(int index, int x) throws GLException
	{
		GL30.glVertexAttribI1i(index, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1u(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI1u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1ui(int index, int x) throws GLException
	{
		GL30.glVertexAttribI1ui(index, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI2(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2i(int index, int x, int y) throws GLException
	{
		GL30.glVertexAttribI2i(index, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2u(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI2u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2ui(int index, int x, int y) throws GLException
	{
		GL30.glVertexAttribI2ui(index, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI3(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3i(int index, int x, int y, int z) throws GLException
	{
		GL30.glVertexAttribI3i(index, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3u(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI3u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3ui(int index, int x, int y, int z) throws GLException
	{
		GL30.glVertexAttribI3ui(index, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI4(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4i(int index, int x, int y, int z, int w) throws GLException
	{
		GL30.glVertexAttribI4i(index, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4u(int index, IntBuffer v) throws GLException
	{
		GL30.glVertexAttribI4u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4ui(int index, int x, int y, int z, int w) throws GLException
	{
		GL30.glVertexAttribI4ui(index, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, IntBuffer buffer) throws GLException
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, long buffer_buffer_offset) throws GLException
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer_buffer_offset);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP1u(int index, int type, boolean normalized, IntBuffer value) throws GLException
	{
		GL33.glVertexAttribP1u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP1ui(int index, int type, boolean normalized, int value) throws GLException
	{
		GL33.glVertexAttribP1ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP2u(int index, int type, boolean normalized, IntBuffer value) throws GLException
	{
		GL33.glVertexAttribP2u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP2ui(int index, int type, boolean normalized, int value) throws GLException
	{
		GL33.glVertexAttribP2ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP3u(int index, int type, boolean normalized, IntBuffer value) throws GLException
	{
		GL33.glVertexAttribP3u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP3ui(int index, int type, boolean normalized, int value) throws GLException
	{
		GL33.glVertexAttribP3ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP4u(int index, int type, boolean normalized, IntBuffer value) throws GLException
	{
		GL33.glVertexAttribP4u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP4ui(int index, int type, boolean normalized, int value) throws GLException
	{
		GL33.glVertexAttribP4ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP2u(int type, IntBuffer value) throws GLException
	{
		GL33.glVertexP2u(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP2ui(int type, int value) throws GLException
	{
		GL33.glVertexP2ui(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP3u(int type, IntBuffer value) throws GLException
	{
		GL33.glVertexP3u(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP3ui(int type, int value) throws GLException
	{
		GL33.glVertexP3ui(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP4u(int type, IntBuffer value) throws GLException
	{
		GL33.glVertexP4u(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP4ui(int type, int value) throws GLException
	{
		GL33.glVertexP4ui(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glWaitSync(long sync, int flags, long timeout) throws GLException
	{
		GL32.glWaitSync(sync, flags, timeout);
		
		RenderHelper.checkForGLError();
		
	}
	
}
