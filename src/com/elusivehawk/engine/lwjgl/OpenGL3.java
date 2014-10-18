
package com.elusivehawk.engine.lwjgl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.render.gl.GLException;
import com.elusivehawk.engine.render.gl.IGL3;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGL3 extends OpenGL2 implements IGL3
{
	@Override
	public void glBeginConditionalRender(int id, int mode) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBeginTransformFeedback(int primitiveMode) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindBufferBase(int target, int index, int buffer)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindBufferRange(int target, int index, int buffer,
			long offset, long size) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindFragDataLocation(int program, int colorNumber, String name)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindFragDataLocationIndexed(int program, int colorNumber,
			int index, String name) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindFramebuffer(int target, int framebuffer)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindRenderbuffer(int target, int renderbuffer)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindSampler(int unit, int sampler) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindSamplers(int first, int argcount, IntBuffer samplers)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBindVertexArray(int array) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1,
			int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int glCheckFramebufferStatus(int target) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void glClampColor(int target, int clamp) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glClearBuffer(int buffer, int drawbuffer, int type, Buffer value)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glClearBufferfi(int buffer, int drawbuffer, float depth,
			int stencil) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glClearBufferu(int buffer, int drawbuffer, IntBuffer value)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int glClientWaitSync(long sync, int flags, long timeout)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glCopyBufferSubData(int readtarget, int writetarget,
			long readoffset, long writeoffset, long size) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteFramebuffer(int framebuffer) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteFramebuffers(int[] framebuffers) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteRenderbuffer(int renderbuffer) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteRenderbuffers(int[] renderbuffer) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteSampler(int sampler) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteSamplers(int[] samplers) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteSync(long sync) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteVertexArray(int array) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDeleteVertexArrays(int[] arrays) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDisablei(int target, int index) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDrawArraysInstanced(int mode, int first, int argcount,
			int primcount) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDrawElementsBaseVertex(int mode, int indices_count, int type,
			long indices_buffer_offset, int basevertex) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDrawElementsIndirect(int mode, int type,
			long indirect_buffer_offset) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDrawElementsInstanced(int mode, int indices_count, int type,
			long indices_buffer_offset, int primcount) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDrawElementsInstancedBaseVertex(int mode, int indices_count,
			int type, long indices_buffer_offset, int primcount, int basevertex)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glDrawRangeElementsBaseVertex(int mode, int start, int end,
			int indices_count, int type, long indices_buffer_offset,
			int basevertex) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glEnablei(int target, int index) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glEndConditionalRender() throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glEndTransformFeedback() throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public long glFenceSync(int condition, int flags) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void glFlushMappedBufferRange(int target, long offset, long length)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glFramebufferRenderbuffer(int target, int attachment,
			int renderbuffertarget, int renderbuffer) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glFramebufferTexture(int target, int attachment, int texture,
			int level) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glFramebufferTexture1D(int target, int attachment,
			int textarget, int texture, int level) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glFramebufferTexture2D(int target, int attachment,
			int textarget, int texture, int level) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glFramebufferTexture3D(int target, int attachment,
			int textarget, int texture, int level, int zoffset)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glFramebufferTextureLayer(int target, int attachment,
			int texture, int level, int layer) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glGenerateMipmap(int target) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int glGenFramebuffer() throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGenFramebuffers(int argcount) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGenRenderbuffer() throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGenRenderbuffers(int argcount) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGenSampler() throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGenSamplers(int argcount) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGenVertexArray() throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGenVertexArrays(int argcount) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetActiveUniformBlocki(int program, int uniformBlockIndex,
			int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String glGetActiveUniformBlockName(int program,
			int uniformBlockIndex, int bufSize) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String glGetActiveUniformName(int program, int uniformIndex,
			int bufSize) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetActiveUniforms(int program, int[] uniformIndices,
			int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetActiveUniformsi(int program, int uniformIndex, int pname)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean glGetBoolean(int value, int index) throws GLException
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean[] glGetBoolean(int value, int index, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long[] glGetBufferParameter(int target, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long glGetBufferParameteri64(int target, int pname)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int glGetFragDataIndex(int program, String name) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int glGetFragDataLocation(int program, String name)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetFramebufferAttachmentParameter(int target,
			int attachment, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetFramebufferAttachmentParameteri(int target, int attachment,
			int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int glGetIntegeri(int value, int index) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetInteger(int value, int index, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long glGetInteger64i(int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long glGetInteger64i(int value, int index) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long[] glGetInteger64(int value, int index, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long[] glGetInteger64(int pname, int argcount) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public float[] glGetMultisample(int pname, int index, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long[] glGetQueryObject(int id, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long glGetQueryObjecti64(int id, int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetQueryObjectu(int id, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long[] glGetQueryObjectu64(int id, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long glGetQueryObjectui64(int id, int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetRenderbufferParameter(int target, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetRenderbufferParameteri(int target, int pname)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public float[] glGetSamplerParameter(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetSamplerParameteri(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public float glGetSamplerParameterf(int sampler, int pname)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int glGetSamplerParameteri(int sampler, int pname)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetSamplerParameterI(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetSamplerParameterIi(int sampler, int pname)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetSamplerParameterIu(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetSamplerParameterIui(int sampler, int pname)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String glGetStringi(int name, int index) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetSync(long sync, int pname, int[] length, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetSynci(long sync, int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetTexParameterI(int target, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetTexParameterIi(int target, int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetTexParameterIu(int target, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetTexParameterIui(int target, int pname) throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String glGetTransformFeedbackVarying(int program, int index,
			int bufSize, int[] size, int[] type) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int glGetUniformBlockIndex(int program, String uniformBlockName)
			throws GLException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int[] glGetUniformIndices(int program, String[] uniformNames)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetUniformu(int program, int location, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public double[] glGetVertexAttrib(int index, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public float[] glGetVertexAttribf(int index, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetVertexAttribI(int index, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetVertexAttribIu(int index, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean glIsEnabledi(int target, int index) throws GLException
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean glIsFramebuffer(int framebuffer)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean glIsRenderbuffer(int renderbuffer)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean glIsSampler(int sampler)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean glIsSync(long sync)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean glIsVertexArray(int array)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ByteBuffer glMapBufferRange(int target, long offset, long length,
			int access, ByteBuffer old_buffer) throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void glPrimitiveRestartIndex(int index)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glProvokingVertex(int mode) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glQueryCounter(int id, int target) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glRenderbufferStorage(int target, int internalformat,
			int width, int height) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glRenderbufferStorageMultisample(int target, int samples,
			int internalformat, int width, int height) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glSampleMaski(int index, int mask) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public float[] glSamplerParameterf(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glSamplerParameter(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void glSamplerParameterf(int sampler, int pname, float param)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glSamplerParameteri(int sampler, int pname, int param)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int[] glSamplerParameterI(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glSamplerParameterIu(int sampler, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void glTexBuffer(int target, int internalformat, int buffer)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glTexImage2DMultisample(int target, int samples,
			int internalformat, int width, int height,
			boolean fixedsamplelocations) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glTexImage3DMultisample(int target, int samples,
			int internalformat, int width, int height, int depth,
			boolean fixedsamplelocations) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int[] glTexParameterI(int target, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void glTexParameterIi(int target, int pname, int param)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int[] glTexParameterIu(int target, int pname, int argcount)
			throws GLException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void glTexParameterIui(int target, int pname, int param)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glTransformFeedbackVaryings(int program, String[] varyings,
			int bufferMode) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glUniformBlockBinding(int program, int uniformBlockIndex,
			int uniformBlockBinding) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribDivisor(int index, int divisor)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI1(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI1i(int index, int x) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI1u(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI1ui(int index, int x) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI2(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI2i(int index, int x, int y) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI2u(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI2ui(int index, int x, int y) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI3(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI3i(int index, int x, int y, int z)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI3u(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI3ui(int index, int x, int y, int z)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI4(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI4i(int index, int x, int y, int z, int w)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI4u(int index, int[] v) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribI4ui(int index, int x, int y, int z, int w)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribIPointer(int index, int size, int type,
			int stride, int[] buffer) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribIPointer(int index, int size, int type,
			int stride, long buffer_buffer_offset) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP1u(int index, int type, boolean normalized,
			int[] value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP1ui(int index, int type, boolean normalized,
			int value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP2u(int index, int type, boolean normalized,
			int[] value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP2ui(int index, int type, boolean normalized,
			int value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP3u(int index, int type, boolean normalized,
			int[] value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP3ui(int index, int type, boolean normalized,
			int value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP4u(int index, int type, boolean normalized,
			int[] value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexAttribP4ui(int index, int type, boolean normalized,
			int value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexP2u(int type, int[] value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexP2ui(int type, int value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexP3u(int type, int[] value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexP3ui(int type, int value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexP4u(int type, int[] value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glVertexP4ui(int type, int value) throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glWaitSync(long sync, int flags, long timeout)
			throws GLException
	{
		// TODO Auto-generated method stub
		
	}
	
}
